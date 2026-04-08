package com.slashphone.launcher.ui.inbox

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.slashphone.launcher.data.entity.CaughtNotification
import com.slashphone.launcher.notification.NotificationRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class InboxViewModel @Inject constructor(
    private val repository: NotificationRepository,
) : ViewModel() {

    val notifications: StateFlow<List<CaughtNotification>> = repository
        .getActiveNotifications()
        .stateIn(viewModelScope, SharingStarted.Lazily, emptyList())

    fun dismiss(id: Long) {
        viewModelScope.launch { repository.dismiss(id) }
    }

    fun dismissAll() {
        viewModelScope.launch { repository.dismissAll() }
    }
}
