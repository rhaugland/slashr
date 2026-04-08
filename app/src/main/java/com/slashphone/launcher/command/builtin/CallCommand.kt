package com.slashphone.launcher.command.builtin

import com.slashphone.launcher.command.Command
import com.slashphone.launcher.command.CommandResult

data class ContactMatch(
    val name: String,
    val phoneNumber: String,
)

class CallCommand(
    private val contactLookup: suspend (String) -> List<ContactMatch>,
    private val dialer: suspend (String) -> Unit,
) : Command {
    override val name = "call"
    override val description = "Call someone — /call [name]"

    override suspend fun execute(args: String): CommandResult {
        if (args.isBlank()) {
            return CommandResult.Error("Who do you want to call? Try /call Mom")
        }

        val matches = contactLookup(args)
        if (matches.isEmpty()) {
            return CommandResult.Error("No contact found for \"$args\"")
        }

        val contact = matches.first()
        dialer(contact.phoneNumber)
        return CommandResult.Launched
    }
}
