package com.slashphone.launcher.command.builtin

import com.slashphone.launcher.command.Command
import com.slashphone.launcher.command.CommandResult

class SearchCommand(
    private val openBrowser: suspend (String) -> Unit,
) : Command {
    override val name = "search"
    override val description = "Search the web"

    override suspend fun execute(args: String): CommandResult {
        if (args.isBlank()) {
            return CommandResult.Error("What are you looking for? Try /search best coffee nearby")
        }
        openBrowser(args)
        return CommandResult.Launched
    }
}
