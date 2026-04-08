package com.slashphone.launcher.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.slashphone.launcher.data.dao.NotificationDao
import com.slashphone.launcher.data.dao.UserPrefsDao
import com.slashphone.launcher.data.dao.WhitelistDao
import com.slashphone.launcher.data.entity.CaughtNotification
import com.slashphone.launcher.data.entity.UserPrefs
import com.slashphone.launcher.data.entity.WhitelistEntry

@Database(
    entities = [
        WhitelistEntry::class,
        CaughtNotification::class,
        UserPrefs::class,
    ],
    version = 1,
    exportSchema = false,
)
abstract class SlashDatabase : RoomDatabase() {
    abstract fun whitelistDao(): WhitelistDao
    abstract fun notificationDao(): NotificationDao
    abstract fun userPrefsDao(): UserPrefsDao

    companion object {
        fun create(context: Context): SlashDatabase {
            return Room.databaseBuilder(
                context,
                SlashDatabase::class.java,
                "slash.db",
            ).build()
        }
    }
}
