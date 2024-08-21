package com.knomster.mobile_kindeev63.domain.interfaces

import com.knomster.mobile_kindeev63.domain.entities.responses.AllPlacesResponse
import com.knomster.mobile_kindeev63.domain.entities.responses.DetailsPlaceDataResponse
import com.knomster.mobile_kindeev63.domain.entities.responses.PlacePhoto

interface PlacesApi {
    fun getAllPlacesData(latitude: Double, longitude: Double): AllPlacesResponse?

    fun getPlaceDetails(id: String): DetailsPlaceDataResponse?

    fun getPlacePhotos(id: String): List<PlacePhoto>?
}