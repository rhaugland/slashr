package com.slashphone.launcher.ui.onboarding

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.slashphone.launcher.data.dao.UserPrefsDao
import com.slashphone.launcher.data.dao.WhitelistDao
import com.slashphone.launcher.data.entity.UserPrefs
import com.slashphone.launcher.data.entity.WhitelistEntry
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

enum class OnboardingStep {
    WELCOME,
    WHITELIST_CONTACTS,
    WHITELIST_APPS,
    READY,
}

data class OnboardingState(
    val step: OnboardingStep = OnboardingStep.WELCOME,
    val selectedContacts: List<WhitelistEntry> = emptyList(),
    val selectedApps: List<WhitelistEntry> = emptyList(),
)

@HiltViewModel
class OnboardingViewModel @Inject constructor(
    private val whitelistDao: WhitelistDao,
    private val userPrefsDao: UserPrefsDao,
) : ViewModel() {

    private val _state = MutableStateFlow(OnboardingState())
    val state: StateFlow<OnboardingState> = _state.asStateFlow()

    fun nextStep() {
        val current = _state.value.step
        val next = when (current) {
            OnboardingStep.WELCOME -> OnboardingStep.WHITELIST_CONTACTS
            OnboardingStep.WHITELIST_CONTACTS -> OnboardingStep.WHITELIST_APPS
            OnboardingStep.WHITELIST_APPS -> OnboardingStep.READY
            OnboardingStep.READY -> OnboardingStep.READY
        }
        _state.value = _state.value.copy(step = next)
    }

    fun addWhitelistContact(name: String, identifier: String) {
        val entry = WhitelistEntry(
            type = "contact",
            identifier = identifier,
            displayName = name,
        )
        _state.value = _state.value.copy(
            selectedContacts = _state.value.selectedContacts + entry,
        )
    }

    fun addWhitelistApp(packageName: String, appName: String) {
        val entry = WhitelistEntry(
            type = "app",
            identifier = packageName,
            displayName = appName,
        )
        _state.value = _state.value.copy(
            selectedApps = _state.value.selectedApps + entry,
        )
    }

    fun completeOnboarding(onComplete: () -> Unit) {
        viewModelScope.launch {
            (_state.value.selectedContacts + _state.value.selectedApps).forEach {
                whitelistDao.insert(it)
            }
            userPrefsDao.set(UserPrefs(key = "onboarding_complete", value = "true"))
            onComplete()
        }
    }
}
