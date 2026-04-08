package com.slashphone.launcher.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.slashphone.launcher.data.entity.UserPrefs

@Dao
interface UserPrefsDao {
    @Query("SELECT value FROM user_prefs WHERE `key` = :key")
    suspend fun get(key: String): String?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun set(prefs: UserPrefs)
}
