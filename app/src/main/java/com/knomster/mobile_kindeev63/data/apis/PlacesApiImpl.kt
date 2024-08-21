package com.knomster.mobile_kindeev63.data.apis

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.knomster.mobile_kindeev63.data.apis.clients.PlacesRetrofitClient
import com.knomster.mobile_kindeev63.data.apis.services.PlacesService
import com.knomster.mobile_kindeev63.domain.entities.responses.AllPlacesResponse
import com.knomster.mobile_kindeev63.domain.entities.responses.DetailsPlaceDataResponse
import com.knomster.mobile_kindeev63.domain.entities.responses.PlacePhoto
import com.knomster.mobile_kindeev63.domain.interfaces.PlacesApi
import java.util.Locale


class PlacesApiImpl : PlacesApi {

    /**
     * Получение мест поблизости
     */
    override fun getAllPlacesData(latitude: Double, longitude: Double): AllPlacesResponse? {
        try {
            val client = PlacesRetrofitClient.getClient("https://api.foursquare.com/v3/")
            val placesService = client.create(PlacesService::class.java)
            val call = placesService.getAllPlaces(
                latitudeAndLongitude = "$latitude,$longitude",
                langCode = Locale.getDefault().language
            )
            val response = call.execute()
            if (response.isSuccessful) {
                val data = response.body()?.string() ?: return null
                return Gson().fromJson(data, AllPlacesResponse::class.java)
            }
            return null
        } catch (_: Exception) {
            return null
        }
    }

    /**
     * Получение подробной информации о месте
     */
    override fun getPlaceDetails(id: String): DetailsPlaceDataResponse? {
        try {
            val client = PlacesRetrofitClient.getClient("https://api.foursquare.com/v3/")
            val placesService = client.create(PlacesService::class.java)
            val call = placesService.getPlaceDetails(
                id = id,
                langCode = Locale.getDefault().language
            )
            val response = call.execute()
            if (response.isSuccessful) {
                val data = response.body()?.string() ?: return null
                return Gson().fromJson(data, DetailsPlaceDataResponse::class.java)
            }
            return null
        } catch (_: Exception) {
            return null
        }
    }

    /**
     * Получение 10 самых популярных фотографий места
     */
    override fun getPlacePhotos(id: String): List<PlacePhoto>? {
        try {
            val client = PlacesRetrofitClient.getClient("https://api.foursquare.com/v3/")
            val placesService = client.create(PlacesService::class.java)
            val call = placesService.getPlacePhotos(
                id = id
            )

            val response = call.execute()
            if (response.isSuccessful) {
                val data = response.body()?.string() ?: return null
                val listType = object : TypeToken<List<PlacePhoto>?>() {}.type
                return Gson().fromJson(data, listType)
            }
            return null
        } catch (_: Exception) {
            return null
        }
    }
}