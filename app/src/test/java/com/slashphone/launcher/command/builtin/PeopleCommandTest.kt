package com.slashphone.launcher.command.builtin

import com.slashphone.launcher.command.CommandResult
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertTrue
import org.junit.Test

class PeopleCommandTest {

    @Test
    fun `shows contacts sorted by last contact time`() = runTest {
        val command = PeopleCommand(
            getInnerCircle = {
                listOf(
                    PersonSummary("Dad", "3 weeks ago"),
                    PersonSummary("Mom", "2 days ago"),
                    PersonSummary("Alex", "5 days ago"),
                )
            }
        )
        val result = command.execute("")
        assertTrue(result is CommandResult.Text)
        val text = (result as CommandResult.Text).content
        assertTrue(text.indexOf("Dad") < text.indexOf("Alex"))
        assertTrue(text.indexOf("Alex") < text.indexOf("Mom"))
    }
}
