package com.knomster.mobile_kindeev63.data.database

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.knomster.mobile_kindeev63.domain.entities.DetailPlaceData

class DetailPlaceDataTypeConverter {
    private val gson = Gson()

    @TypeConverter
    fun fromDetailPlaceData(detailPlaceData: DetailPlaceData): String {
        return gson.toJson(detailPlaceData)
    }

    @TypeConverter
    fun toDetailPlaceData(data: String): DetailPlaceData {
        val type = object : TypeToken<DetailPlaceData>() {}.type
        return gson.fromJson(data, type)
    }
}