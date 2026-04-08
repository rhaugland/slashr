package com.slashphone.launcher.command.builtin

import com.slashphone.launcher.command.Command
import com.slashphone.launcher.command.CommandResult

class InboxCommand : Command {
    override val name = "inbox"
    override val description = "Notifications caught by the gatekeeper"

    override suspend fun execute(args: String): CommandResult {
        return CommandResult.Navigate("inbox")
    }
}
