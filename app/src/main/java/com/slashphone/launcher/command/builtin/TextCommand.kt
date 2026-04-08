package com.slashphone.launcher.command.builtin

import com.slashphone.launcher.command.Command
import com.slashphone.launcher.command.CommandResult

class TextCommand(
    private val contactLookup: suspend (String) -> List<ContactMatch>,
    private val openSms: suspend (String) -> Unit,
) : Command {
    override val name = "text"
    override val description = "Text someone — /text [name]"

    override suspend fun execute(args: String): CommandResult {
        if (args.isBlank()) {
            return CommandResult.Error("Who do you want to text? Try /text Mom")
        }

        val matches = contactLookup(args)
        if (matches.isEmpty()) {
            return CommandResult.Error("No contact found for \"$args\"")
        }

        openSms(matches.first().phoneNumber)
        return CommandResult.Launched
    }
}
