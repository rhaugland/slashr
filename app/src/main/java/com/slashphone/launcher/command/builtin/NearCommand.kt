package com.slashphone.launcher.command.builtin

import com.slashphone.launcher.command.Command
import com.slashphone.launcher.command.CommandResult

class NearCommand(
    private val openMapsSearch: suspend (String) -> Unit,
) : Command {
    override val name = "near"
    override val description = "Find nearby places — /near [thing]"

    override suspend fun execute(args: String): CommandResult {
        if (args.isBlank()) {
            return CommandResult.Error("What are you looking for? Try /near coffee or /near gas")
        }
        openMapsSearch(args)
        return CommandResult.Launched
    }
}
