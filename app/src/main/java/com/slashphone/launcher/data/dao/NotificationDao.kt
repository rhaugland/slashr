package com.slashphone.launcher.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.slashphone.launcher.data.entity.CaughtNotification
import kotlinx.coroutines.flow.Flow

@Dao
interface NotificationDao {
    @Query("SELECT * FROM caught_notifications WHERE dismissed = 0 ORDER BY timestamp DESC")
    fun getActive(): Flow<List<CaughtNotification>>

    @Query("SELECT COUNT(*) FROM caught_notifications WHERE dismissed = 0")
    fun getActiveCount(): Flow<Int>

    @Insert
    suspend fun insert(notification: CaughtNotification)

    @Query("UPDATE caught_notifications SET dismissed = 1 WHERE id = :id")
    suspend fun dismiss(id: Long)

    @Query("UPDATE caught_notifications SET dismissed = 1")
    suspend fun dismissAll()

    @Query("DELETE FROM caught_notifications WHERE timestamp < :before")
    suspend fun deleteOlderThan(before: Long)
}
