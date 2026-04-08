package com.slashphone.launcher.command.builtin

import com.slashphone.launcher.command.Command
import com.slashphone.launcher.command.CommandResult

class TomorrowCommand(
    private val getCalendarEvents: suspend () -> List<CalendarEvent>,
) : Command {
    override val name = "tomorrow"
    override val description = "What's ahead tomorrow"

    override suspend fun execute(args: String): CommandResult {
        val events = getCalendarEvents()
        if (events.isEmpty()) {
            return CommandResult.Text("Tomorrow is clear.")
        }
        val formatted = events.joinToString("\n") { "${it.time}  ${it.title}" }
        return CommandResult.Text(formatted)
    }
}
