package com.knomster.mobile_kindeev63.domain.entities.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "table_notes")
data class Note(
    @PrimaryKey(autoGenerate = false)
    val id: Int,
    @ColumnInfo(name = "date")
    val date: Long?,
    @ColumnInfo(name = "title")
    val title: String,
    @ColumnInfo(name = "text")
    val text: String,
    @ColumnInfo(name = "pinnedPlaceId")
    val pinnedPlaceId: String?
): Serializable
