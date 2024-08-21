package com.knomster.userslibrary.domain.entities

import com.google.gson.annotations.SerializedName

data class UserDataResponse(
    @SerializedName("results")
    val results: List<User>
) {
    data class User(
        @SerializedName("name")
        val name: Name,
        @SerializedName("email")
        val email: String,
        @SerializedName("login")
        val login: Login,
        @SerializedName("picture")
        val picture: Picture
    ) {
        data class Name(
            @SerializedName("first")
            val first: String,
            @SerializedName("last")
            val last: String
        )

        data class Login(
            @SerializedName("username")
            val username: String,
            @SerializedName("password")
            val password: String
        )

        data class Picture(
            @SerializedName("large")
            val large: String,
            @SerializedName("medium")
            val medium: String,
            @SerializedName("thumbnail")
            val thumbnail: String
        )
    }
}