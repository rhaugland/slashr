package com.slashphone.launcher.command.builtin

import com.slashphone.launcher.command.Command
import com.slashphone.launcher.command.CommandResult

class GoCommand(
    private val openMaps: suspend (String) -> Unit,
) : Command {
    override val name = "go"
    override val description = "Get directions — /go [place]"

    override suspend fun execute(args: String): CommandResult {
        if (args.isBlank()) {
            return CommandResult.Error("Where to? Try /go home or /go 123 Main St")
        }
        openMaps(args)
        return CommandResult.Launched
    }
}
