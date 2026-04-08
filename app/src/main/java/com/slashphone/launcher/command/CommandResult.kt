package com.slashphone.launcher.command

sealed class CommandResult {
    data class Text(val content: String) : CommandResult()
    data object Launched : CommandResult()
    data class Navigate(val route: String) : CommandResult()
    data class Error(val message: String) : CommandResult()
    data class NotFound(
        val input: String,
        val suggestions: List<String> = emptyList(),
    ) : CommandResult()
}
