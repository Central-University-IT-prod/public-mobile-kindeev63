package com.knomster.mobile_kindeev63.domain.entities

import java.io.Serializable

data class DetailPlaceData(
    val id: String,
    val name: String,
    val categories: List<PlaceCategory>?,
    val description: String?,
    val email: String?,
    val fax: String?,
    val acceptCreditCard: Boolean?,
    val delivery: Boolean?,
    val restroom: Boolean?,
    val music: Boolean?,
    val liveMusic: Boolean?,
    val privateRoom: Boolean?,
    val outdoorSeating: Boolean?,
    val wheelchairAccessible: Boolean?,
    val parking: Boolean?,
    val latitude: Double?,
    val longitude: Double?,
    val isOpenNow: Boolean?,
    val workingTime: List<WorkTime>?,
    val address: String?,
    val menuUrl: String?,
    val photosUrls: List<String>?,
    val facebookId: String?,
    val instagram: String?,
    val twitter: String?,
    val telephone: String?,
    val tips: List<PlaceTip>?,
    val website: String?
): Serializable

data class WorkTime(
    val day: Int,
    val workingTime: String
): Serializable

data class PlaceTip(
    val text: String,
    val photoUrl: String?,
    val createdAt: String?
): Serializable