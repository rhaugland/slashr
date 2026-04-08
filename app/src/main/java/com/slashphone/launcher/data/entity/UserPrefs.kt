package com.slashphone.launcher.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user_prefs")
data class UserPrefs(
    @PrimaryKey val key: String,
    val value: String,
)
