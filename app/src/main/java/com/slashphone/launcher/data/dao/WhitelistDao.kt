package com.slashphone.launcher.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.slashphone.launcher.data.entity.WhitelistEntry
import kotlinx.coroutines.flow.Flow

@Dao
interface WhitelistDao {
    @Query("SELECT * FROM whitelist ORDER BY displayName")
    fun getAll(): Flow<List<WhitelistEntry>>

    @Query("SELECT * FROM whitelist WHERE type = :type")
    fun getByType(type: String): Flow<List<WhitelistEntry>>

    @Query("SELECT identifier FROM whitelist")
    suspend fun getAllIdentifiers(): List<String>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(entry: WhitelistEntry)

    @Delete
    suspend fun delete(entry: WhitelistEntry)
}
