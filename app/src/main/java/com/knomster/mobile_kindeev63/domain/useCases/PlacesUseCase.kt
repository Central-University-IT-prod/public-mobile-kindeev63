package com.knomster.mobile_kindeev63.domain.useCases

import com.knomster.mobile_kindeev63.domain.entities.DetailPlaceData
import com.knomster.mobile_kindeev63.domain.entities.PlaceCategory
import com.knomster.mobile_kindeev63.domain.entities.PlaceData
import com.knomster.mobile_kindeev63.domain.entities.PlaceTip
import com.knomster.mobile_kindeev63.domain.entities.PlacesPage
import com.knomster.mobile_kindeev63.domain.entities.WorkTime
import com.knomster.mobile_kindeev63.domain.interfaces.PlacesApi
import java.text.SimpleDateFormat
import java.util.Locale

class PlacesUseCase(private val placesApi: PlacesApi) {
    fun getAllPlaces(latitude: Double, longitude: Double): List<PlacesPage> {
        val allPlaces = placesApi.getAllPlacesData(latitude, longitude) ?: return emptyList()
        val placesList = mutableListOf<PlaceData>()
        allPlaces.results.forEach { place ->
            var photoUrl: String? = null
            if (place.photos.isNotEmpty()) {
                val photo = place.photos.first()
                photoUrl = photo.prefix + "original" + photo.suffix
            }
            placesList.add(
                PlaceData(
                    id = place.id,
                    name = place.name,
                    address = place.location.formattedAddress,
                    photoUrl = photoUrl,
                    categories = place.categories.map { category ->
                        PlaceCategory(
                            name = category.name,
                            iconUrl = category.icon.prefix + "120" + category.icon.suffix
                        )
                    }
                )
            )
        }
        return placesList.slice()
    }

    private fun List<PlaceData>.slice(chunkSize: Int = 10): List<PlacesPage> {
        val result = mutableListOf<PlacesPage>()
        for (i in this.indices step chunkSize) {
            val chunk = this.subList(i, minOf(i + chunkSize, this.size))
            result.add(PlacesPage(chunk))
        }
        return result
    }

    suspend fun getPlaceData(
        id: String,
        cacheUseCase: CacheUseCase,
        onGet: (DetailPlaceData?) -> Unit
    ) {
        val cache = cacheUseCase.getCacheDyId(id = id)
        if (cache == null) {
            val placeResponse = placesApi.getPlaceDetails(id)
            val originalTipDateFormat =
                SimpleDateFormat(
                    "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'",
                    Locale.getDefault()
                )
            val targetTipDateFormat =
                SimpleDateFormat(
                    "dd.MM.yyyy HH.mm",
                    Locale.getDefault()
                )
            val detailPlaceData =
                if (placeResponse == null) {
                    null
                } else {
                    DetailPlaceData(
                        id = placeResponse.id,
                        name = placeResponse.name,
                        description = placeResponse.description,
                        fax = placeResponse.fax,
                        email = placeResponse.email,
                        delivery = placeResponse.features?.servicesFeatures?.delivery?.equals(
                            false
                        )
                            ?.not(),
                        restroom = placeResponse.features?.amenitiesFeatures?.restroom?.equals(
                            false
                        )
                            ?.not(),
                        liveMusic = placeResponse.features?.amenitiesFeatures?.liveMusic?.equals(
                            false
                        )?.not(),
                        music = placeResponse.features?.amenitiesFeatures?.music?.equals(false)
                            ?.not(),
                        outdoorSeating = placeResponse.features?.amenitiesFeatures?.outdoorSeating?.equals(
                            false
                        )?.not(),
                        parking = placeResponse.features?.amenitiesFeatures?.parking?.equals(
                            false
                        )
                            ?.not(),
                        privateRoom = placeResponse.features?.amenitiesFeatures?.privateRoom?.equals(
                            false
                        )?.not(),
                        menuUrl = placeResponse.menu,
                        isOpenNow = placeResponse.hours?.isOpenNow,
                        acceptCreditCard = placeResponse.features?.paymentFeatures?.creditCards?.equals(
                            false
                        )?.not(),
                        wheelchairAccessible = placeResponse.features?.amenitiesFeatures?.wheelchairAccessible?.equals(
                            false
                        )?.not(),
                        address = placeResponse.location?.formattedAddress,
                        photosUrls = getAllPlacePhotos(id = id),
                        categories = placeResponse.categories?.map { category ->
                            PlaceCategory(
                                name = category.name,
                                iconUrl = category.icon.prefix + "120" + category.icon.suffix
                            )
                        },
                        latitude = placeResponse.geocodes?.main?.latitude,
                        longitude = placeResponse.geocodes?.main?.longitude,
                        workingTime = if (placeResponse.hours?.regularHours == null) {
                            null
                        } else {
                            mutableListOf<WorkTime>().apply {
                                (1..7).forEach { day ->
                                    add(
                                        WorkTime(
                                            day = day,
                                            workingTime = placeResponse.hours.regularHours.filter { it.day == day }
                                                .joinToString("\n") {
                                                    "${
                                                        it.openTime
                                                            ?.reversed()
                                                            ?.chunked(2)
                                                            ?.joinToString(":")
                                                            ?.reversed()
                                                    }-${
                                                        it.closeTime
                                                            ?.reversed()
                                                            ?.chunked(2)
                                                            ?.joinToString(":")
                                                            ?.reversed()
                                                    }"
                                                }
                                        )
                                    )
                                }
                                this.sortBy { it.day }
                            }
                        },
                        facebookId = placeResponse.socialMedia?.facebookId,
                        instagram = placeResponse.socialMedia?.instagram,
                        twitter = placeResponse.socialMedia?.twitter,
                        telephone = placeResponse.telephone,
                        tips = placeResponse.tips?.map { tip ->
                            PlaceTip(
                                text = tip.text,
                                photoUrl = tip.photoUrl,
                                createdAt = try {
                                    val date = originalTipDateFormat.parse(tip.createdAt)
                                    if (date == null) {
                                        null
                                    } else {
                                        targetTipDateFormat.format(date)
                                    }
                                } catch (_: Exception) {
                                    null
                                }

                            )
                        },
                        website = placeResponse.website
                    )
                }
            detailPlaceData?.let { cacheUseCase.insertCache(it) }
            onGet(detailPlaceData)
        } else {
            onGet(cache.data)
        }
    }

    private fun getAllPlacePhotos(id: String): List<String> {
        return placesApi.getPlacePhotos(id = id)?.map { photo ->
            photo.prefix + "original" + photo.suffix
        }
            ?: return emptyList()
    }
}