package com.knomster.mobile_kindeev63.data.apis.services

import com.knomster.mobile_kindeev63.domain.ProjectConstants
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

interface PlacesService {

    @Headers(
        "Authorization: ${ProjectConstants.placesApiId}",
        "accept: application/json"
    )
    @GET("places/search?fields=fsq_id,name,location,categories,photos")
    fun getAllPlaces(
        @Query("ll") latitudeAndLongitude: String,
        @Header("Accept-Language") langCode: String
    ): Call<ResponseBody>

    @Headers(
        "Authorization: ${ProjectConstants.placesApiId}",
        "accept: application/json"
    )
    @GET("places/{id}?fields=fsq_id,categories,chains,distance,description,email,fax,features,email,geocodes,hours,hours_popular,link,location,name,menu,popularity,price,rating,related_places,social_media,stats,store_id,tastes,tel,timezone,tips,venue_reality_bucket,verified,website,stats")
    fun getPlaceDetails(
        @Path("id") id: String,
        @Header("Accept-Language") langCode: String
    ): Call<ResponseBody>

    @Headers(
        "Authorization: ${ProjectConstants.placesApiId}",
        "accept: application/json"
    )
    @GET("places/{id}/photos?sort=POPULAR")
    fun getPlacePhotos(
        @Path("id") id: String
    ): Call<ResponseBody>
}