package com.slashphone.launcher.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "caught_notifications")
data class CaughtNotification(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val packageName: String,
    val appName: String,
    val title: String,
    val content: String,
    val timestamp: Long,
    val dismissed: Boolean = false,
)
