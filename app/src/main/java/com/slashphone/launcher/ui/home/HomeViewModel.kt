package com.slashphone.launcher.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
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
)

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val contextLineProvider: ContextLineProvider,
) : ViewModel() {

    private val _state = MutableStateFlow(HomeState())
    val state: StateFlow<HomeState> = _state.asStateFlow()

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
        _state.value = _state.value.copy(commandInput = "", isProcessing = true)
        // Command execution handled in Task 3
        _state.value = _state.value.copy(isProcessing = false)
    }

    private fun refreshContextLine() {
        _state.value = _state.value.copy(
            contextLine = contextLineProvider.getContextLine()
        )
    }
}
