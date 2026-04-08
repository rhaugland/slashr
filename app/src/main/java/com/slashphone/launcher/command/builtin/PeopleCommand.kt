package com.slashphone.launcher.command.builtin

import com.slashphone.launcher.command.Command
import com.slashphone.launcher.command.CommandResult

data class PersonSummary(
    val name: String,
    val lastContact: String,
)

class PeopleCommand(
    private val getInnerCircle: suspend () -> List<PersonSummary>,
) : Command {
    override val name = "people"
    override val description = "Your inner circle, sorted by who you haven't talked to longest"

    override suspend fun execute(args: String): CommandResult {
        val people = getInnerCircle()
        if (people.isEmpty()) {
            return CommandResult.Text("No contacts yet. Your inner circle builds as you use /call and /text.")
        }
        val formatted = people.joinToString("\n") { "${it.name} — ${it.lastContact}" }
        return CommandResult.Text(formatted)
    }
}
