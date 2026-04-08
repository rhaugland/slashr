package com.slashphone.launcher.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "whitelist")
data class WhitelistEntry(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val type: String,
    val identifier: String,
    val displayName: String,
)
