package com.slashphone.launcher.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.slashphone.launcher.command.CommandRegistry
import com.slashphone.launcher.command.CommandResult
import com.slashphone.launcher.contextline.ContextLineProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

data class HomeState(
    val contextLine: String = "",
    val commandInput: String = "",
    val isProcessing: Boolean = false,
    val lastResult: CommandResult? = null,
)

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val contextLineProvider: ContextLineProvider,
    private val commandRegistry: CommandRegistry,
) : ViewModel() {

    private val _state = MutableStateFlow(HomeState())
    val state: StateFlow<HomeState> = _state.asStateFlow()

    private val _navigationEvent = MutableStateFlow<String?>(null)
    val navigationEvent: StateFlow<String?> = _navigationEvent.asStateFlow()

    init {
        refreshContextLine()
        viewModelScope.launch {
            while (true) {
                delay(60_000)
                refreshContextLine()
            }
        }
    }

    fun onCommandInputChanged(input: String) {
        _state.value = _state.value.copy(commandInput = input)
    }

    fun onCommandSubmitted() {
        val input = _state.value.commandInput.trim()
        if (input.isEmpty()) return

        _state.value = _state.value.copy(commandInput = "", isProcessing = true, lastResult = null)

        viewModelScope.launch {
            val result = commandRegistry.execute(input)
            _state.value = _state.value.copy(isProcessing = false, lastResult = result)
            if (result is CommandResult.Navigate) {
                _navigationEvent.value = result.route
            }
        }
    }

    fun onNavigationHandled() {
        _navigationEvent.value = null
    }

    fun clearResult() {
        _state.value = _state.value.copy(lastResult = null)
    }

    private fun refreshContextLine() {
        _state.value = _state.value.copy(
            contextLine = contextLineProvider.getContextLine()
        )
    }
}
