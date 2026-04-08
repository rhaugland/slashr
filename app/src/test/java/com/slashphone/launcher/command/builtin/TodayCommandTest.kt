package com.slashphone.launcher.command.builtin

import com.slashphone.launcher.command.CommandResult
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class TodayCommandTest {

    @Test
    fun `shows calendar events for today`() = runTest {
        val command = TodayCommand(
            getCalendarEvents = {
                listOf(
                    CalendarEvent("9:00 AM", "Team standup"),
                    CalendarEvent("2:00 PM", "Design review"),
                )
            }
        )
        val result = command.execute("")
        assertTrue(result is CommandResult.Text)
        val text = (result as CommandResult.Text).content
        assertTrue(text.contains("Team standup"))
        assertTrue(text.contains("Design review"))
    }

    @Test
    fun `shows empty state when no events`() = runTest {
        val command = TodayCommand(getCalendarEvents = { emptyList() })
        val result = command.execute("")
        assertEquals(CommandResult.Text("Nothing on the calendar today. It's yours."), result)
    }
}
