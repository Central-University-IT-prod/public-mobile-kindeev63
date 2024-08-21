package com.knomster.userslibrary.domain.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "table_users")
data class UserData(
    @PrimaryKey(autoGenerate = false)
    val login: String,
    val password: String,
    val firstName: String,
    val lastName: String,
    val email: String,
    val photoUrl: String,
)
