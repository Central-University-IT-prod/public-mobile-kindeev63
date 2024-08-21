package com.knomster.mobile_kindeev63.domain.entities.responses

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class PlacePhoto(
    @SerializedName("id")
    val id: String,
    @SerializedName("created_at")
    val createdAt: String,
    @SerializedName("prefix")
    val prefix: String,
    @SerializedName("suffix")
    val suffix: String,
    @SerializedName("width")
    val width: Int,
    @SerializedName("height")
    val height: Int,
): Serializable