package com.slashphone.launcher.notification

import com.slashphone.launcher.data.dao.NotificationDao
import com.slashphone.launcher.data.dao.WhitelistDao
import com.slashphone.launcher.data.entity.CaughtNotification
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NotificationRepository @Inject constructor(
    private val notificationDao: NotificationDao,
    private val whitelistDao: WhitelistDao,
) {
    fun getActiveNotifications(): Flow<List<CaughtNotification>> {
        return notificationDao.getActive()
    }

    fun getActiveCount(): Flow<Int> {
        return notificationDao.getActiveCount()
    }

    suspend fun catchNotification(
        packageName: String,
        appName: String,
        title: String,
        content: String,
    ) {
        notificationDao.insert(
            CaughtNotification(
                packageName = packageName,
                appName = appName,
                title = title,
                content = content,
                timestamp = System.currentTimeMillis(),
            )
        )
    }

    suspend fun dismiss(id: Long) {
        notificationDao.dismiss(id)
    }

    suspend fun dismissAll() {
        notificationDao.dismissAll()
    }

    suspend fun buildFilter(): NotificationFilter {
        val identifiers = whitelistDao.getAllIdentifiers().toSet()
        return NotificationFilter(whitelistedIdentifiers = identifiers)
    }
}
