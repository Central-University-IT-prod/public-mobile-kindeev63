package com.knomster.mobile_kindeev63.domain.entities.responses

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class AllPlacesResponse(
    @SerializedName("results")
    val results: List<Place>,
): Serializable

data class Place(
    @SerializedName("fsq_id")
    val id: String,
    val name: String,
    @SerializedName("categories")
    val categories: List<Category>,
    @SerializedName("location")
    val location: Location,
    @SerializedName("photos")
    val photos: List<PlacePhoto>
): Serializable

data class Category(
    @SerializedName("id")
    val id: Int,
    @SerializedName("name")
    val name: String,
    @SerializedName("short_name")
    val shortName: String,
    @SerializedName("plural_name")
    val pluralName: String,
    @SerializedName("icon")
    val icon: Icon,
): Serializable

data class Icon(
    @SerializedName("prefix")
    val prefix: String,
    @SerializedName("suffix")
    val suffix: String,
): Serializable

data class Location(
    @SerializedName("address")
    val address: String,
    @SerializedName("country")
    val country: String,
    @SerializedName("cross_street")
    val crossStreet: String,
    @SerializedName("formatted_address")
    val formattedAddress: String,
    @SerializedName("locality")
    val locality: String,
    @SerializedName("postcode")
    val postcode: String,
    @SerializedName("region")
    val region: String
): Serializable