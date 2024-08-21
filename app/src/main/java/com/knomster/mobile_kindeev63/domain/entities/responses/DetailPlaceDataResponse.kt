package com.knomster.mobile_kindeev63.domain.entities.responses


import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class DetailsPlaceDataResponse(
    @SerializedName("fsq_id")
    val id: String,
    val categories: List<DetailCategory>?,
    val chains: List<Chain>,
    @SerializedName("closed_bucket")
    val closedBucket: String?,
    @SerializedName("date_closed")
    val dateClosed: String?,
    val description: String?,
    val distance: Int?,
    val email: String?,
    val fax: String?,
    val features: Features?,
    val geocodes: Geocodes?,
    val hours: Hours?,
    @SerializedName("hours_popular")
    val hoursPopular: List<HoursPopular>,
    val link: String?,
    val location: DetailLocation?,
    val menu: String?,
    val name: String,
    val photos: List<DetailPhoto>,
    val popularity: Double?,
    val price: Int?,
    val rating: Double?,
    @SerializedName("related_places")
    val relatedPlaces: Any?,
    @SerializedName("social_media")
    val socialMedia: SocialMedia?,
    val stats: Stats?,
    @SerializedName("store_id")
    val storeId: String?,
    val tastes: List<String>,
    @SerializedName("tel")
    val telephone: String?,
    val timezone: String?,
    val tips: List<Tip>?,
    @SerializedName("venue_reality_bucket")
    val venueRealityBucket: String?,
    val verified: Boolean?,
    val website: String?
): Serializable

data class DetailCategory(
    @SerializedName("id")
    val id: Int,
    @SerializedName("name")
    val name: String,
    @SerializedName("short_name")
    val shortName: String,
    @SerializedName("plural_name")
    val pluralName: String,
    @SerializedName("icon")
    val icon: DetailIcon
): Serializable

data class DetailIcon(
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
    @SerializedName("classifications")
    val classifications: List<String>,
    @SerializedName("tip")
    val tip: Tip
): Serializable

data class Tip(
    val id: String,
    @SerializedName("created_at")
    val createdAt: String,
    val text: String,
    val url: String,
    @SerializedName("lang")
    val language: String,
    @SerializedName("agree_count")
    val agreeCount: Int,
    @SerializedName("disagree_count")
    val disagreeCount: Int,
    @SerializedName("photo")
    val photoUrl: String?
): Serializable

data class Chain(
    @SerializedName("id")
    val id: String,
    @SerializedName("name")
    val name: String
): Serializable

data class Features(
    @SerializedName("payment")
    val paymentFeatures: PaymentFeatures?,
    @SerializedName("food_and_drink")
    val foodAndDrinkFeatures: FoodAndDrinkFeatures?,
    @SerializedName("services")
    val servicesFeatures: ServicesFeatures?,
    @SerializedName("amenities")
    val amenitiesFeatures: AmenitiesFeatures?,
    @SerializedName("attributes")
    val attributesFeatures: AttributesFeatures?
): Serializable

data class PaymentFeatures(
    @SerializedName("credit_cards")
    val creditCards: Any?,
    @SerializedName("digital_wallet")
    val digitalWallet: Any?
): Serializable

data class FoodAndDrinkFeatures(
    @SerializedName("alcohol")
    val alcohol: Any?,
    @SerializedName("meals")
    val meals: Any?
): Serializable

data class ServicesFeatures(
    @SerializedName("delivery")
    val delivery: Any?,
    @SerializedName("takeout")
    val takeout: Any?,
    @SerializedName("drive_through")
    val driveThrough: Any?,
    @SerializedName("dine_in")
    val dineIn: DineInFeatures?
): Serializable

data class DineInFeatures(
    @SerializedName("reservations")
    val reservations: Any?,
    @SerializedName("online_reservations")
    val onlineReservations: Any?,
    @SerializedName("groups_only_reservations")
    val groupsOnlyReservations: Any?,
    @SerializedName("essential_reservations")
    val essentialReservations: Any?
): Serializable

data class AmenitiesFeatures(
    @SerializedName("restroom")
    val restroom: Any?,
    @SerializedName("smoking")
    val smoking: Any?,
    @SerializedName("jukebox")
    val jukebox: Any?,
    @SerializedName("music")
    val music: Any?,
    @SerializedName("live_music")
    val liveMusic: Boolean?,
    @SerializedName("private_room")
    val privateRoom: Any?,
    @SerializedName("outdoor_seating")
    val outdoorSeating: Boolean?,
    @SerializedName("tvs")
    val tvs: Any?,
    @SerializedName("atm")
    val atm: Any?,
    @SerializedName("coat_check")
    val coatCheck: Any?,
    @SerializedName("wheelchair_accessible")
    val wheelchairAccessible: Boolean?,
    @SerializedName("parking")
    val parking: Any?,
    @SerializedName("sit_down_dining")
    val sitDownDining: Any?,
    @SerializedName("wifi")
    val wifi: String?
): Serializable

data class AttributesFeatures(
    @SerializedName("business_meeting")
    val businessMeeting: String?,
    val clean: String?,
    val crowded: String?,
    @SerializedName("dates_popular")
    val datesPopular: String?,
    val dressy: String?,
    @SerializedName("families_popular")
    val familiesPopular: String?,
    @SerializedName("gluten_free_diet")
    val glutenFreeDiet: String?,
    @SerializedName("good_for_dogs")
    val goodForDogs: String?,
    @SerializedName("groups_popular")
    val groupsPopular: String?,
    @SerializedName("healthy_diet")
    val healthyDiet: String?,
    @SerializedName("late_night")
    val lateNight: String?,
    val noisy: String?,
    @SerializedName("quick_bite")
    val quickBite: String?,
    val romantic: String?,
    @SerializedName("service_quality")
    val serviceQuality: String?,
    @SerializedName("singles_popular")
    val singlesPopular: String?,
    @SerializedName("special_occasion")
    val specialOccasion: String?,
    val trendy: String?,
    @SerializedName("value_for_money")
    val valueForMoney: String?,
    @SerializedName("vegan_diet")
    val veganDiet: String?,
    @SerializedName("vegetarian_diet")
    val vegetarianDiet: String?
): Serializable

data class Geocodes(
    @SerializedName("drop_off")
    val dropOff: Geocode?,
    @SerializedName("front_door")
    val frontDoor: Geocode?,
    val main: Geocode?,
    val road: Geocode?,
    val roof: Geocode?
): Serializable

data class Geocode(
    val latitude: Double,
    val longitude: Double
): Serializable

data class Hours(
    val display: String?,
    @SerializedName("is_local_holiday")
    val isLocalHoliday: Boolean?,
    @SerializedName("open_now")
    val isOpenNow: Boolean?,
    @SerializedName("regular")
    val regularHours: List<RegularHours>?
): Serializable

data class RegularHours(
    @SerializedName("close")
    val closeTime: String?,
    @SerializedName("day")
    val day: Int,
    @SerializedName("open")
    val openTime: String?
): Serializable

data class HoursPopular(
    @SerializedName("close")
    val closeTime: String,
    @SerializedName("day")
    val day: Int,
    @SerializedName("open")
    val openTime: String
): Serializable

data class DetailLocation(
    val address: String?,
    @SerializedName("address_extended")
    val extendedAddress: String?,
    @SerializedName("admin_region")
    val adminRegion: String?,
    @SerializedName("census_block")
    val censusBlock: String?,
    val country: String?,
    @SerializedName("cross_street")
    val crossStreet: String?,
    val dma: String?,
    @SerializedName("formatted_address")
    val formattedAddress: String?,
    val locality: String?,
    val neighborhood: List<String>?,
    @SerializedName("po_box")
    val poBox: String?,
    @SerializedName("post_town")
    val postTown: String?,
    val postcode: String?,
    val region: String?
): Serializable

data class DetailPhoto(
    val id: String,
    @SerializedName("created_at")
    val createdAt: String,
    val prefix: String,
    val suffix: String,
    val width: Int,
    val height: Int,
    val classifications: List<String>,
    val tip: Tip?
): Serializable

data class SocialMedia(
    @SerializedName("facebook_id")
    val facebookId: String?,
    val instagram: String?,
    val twitter: String?
): Serializable

data class Stats(
    @SerializedName("total_photos")
    val totalPhotos: Int?,
    @SerializedName("total_ratings")
    val totalRatings: Int?,
    @SerializedName("total_tips")
    val totalTips: Int?
): Serializable