package com.knomster.mobile_kindeev63.domain.entities.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.knomster.mobile_kindeev63.domain.entities.DetailPlaceData
import java.io.Serializable

@Entity(tableName = "table_cache")
data class CacheData(
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "id")
    val id: String,
    @ColumnInfo(name = "time")
    val time: Long,
    @ColumnInfo(name = "data")
    val data: DetailPlaceData
): Serializable
