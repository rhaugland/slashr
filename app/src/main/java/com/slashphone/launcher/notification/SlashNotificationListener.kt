package com.slashphone.launcher.notification

import android.service.notification.NotificationListenerService
import android.service.notification.StatusBarNotification
import com.slashphone.launcher.data.SlashDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch

class SlashNotificationListener : NotificationListenerService() {

    private val scope = CoroutineScope(SupervisorJob() + Dispatchers.IO)
    private var repository: NotificationRepository? = null

    override fun onCreate() {
        super.onCreate()
        val db = SlashDatabase.create(applicationContext)
        repository = NotificationRepository(
            notificationDao = db.notificationDao(),
            whitelistDao = db.whitelistDao(),
        )
    }

    override fun onNotificationPosted(sbn: StatusBarNotification) {
        val repo = repository ?: return
        val notification = sbn.notification ?: return
        val extras = notification.extras

        val packageName = sbn.packageName ?: return
        val title = extras.getCharSequence("android.title")?.toString() ?: ""
        val content = extras.getCharSequence("android.text")?.toString() ?: ""
        val appName = packageManager.getApplicationLabel(
            packageManager.getApplicationInfo(packageName, 0)
        ).toString()

        scope.launch {
            val filter = repo.buildFilter()
            val decision = filter.decide(packageName, title, content)

            when (decision) {
                FilterDecision.PASS_THROUGH -> {
                    // Let it through
                }
                FilterDecision.CATCH -> {
                    repo.catchNotification(packageName, appName, title, content)
                    cancelNotification(sbn.key)
                }
            }
        }
    }
}
