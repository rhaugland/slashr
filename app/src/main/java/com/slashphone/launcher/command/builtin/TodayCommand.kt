package com.slashphone.launcher.command.builtin

import com.slashphone.launcher.command.Command
import com.slashphone.launcher.command.CommandResult

data class CalendarEvent(
    val time: String,
    val title: String,
)

class TodayCommand(
    private val getCalendarEvents: suspend () -> List<CalendarEvent>,
) : Command {
    override val name = "today"
    override val description = "Your calendar + priorities for today"

    override suspend fun execute(args: String): CommandResult {
        val events = getCalendarEvents()
        if (events.isEmpty()) {
            return CommandResult.Text("Nothing on the calendar today. It's yours.")
        }
        val formatted = events.joinToString("\n") { "${it.time}  ${it.title}" }
        return CommandResult.Text(formatted)
    }
}
