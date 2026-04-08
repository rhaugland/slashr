package com.slashphone.launcher.command.builtin

import com.slashphone.launcher.command.CommandResult
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class CallCommandTest {

    @Test
    fun `returns error when no name provided`() = runTest {
        val command = CallCommand(contactLookup = { emptyList() }, dialer = { })
        val result = command.execute("")
        assertTrue(result is CommandResult.Error)
    }

    @Test
    fun `returns error when contact not found`() = runTest {
        val command = CallCommand(
            contactLookup = { emptyList() },
            dialer = { },
        )
        val result = command.execute("Nobody")
        assertTrue(result is CommandResult.Error)
    }

    @Test
    fun `launches dialer when contact found`() = runTest {
        var dialedNumber: String? = null
        val command = CallCommand(
            contactLookup = { query ->
                if (query == "Mom") listOf(ContactMatch("Mom", "555-1234"))
                else emptyList()
            },
            dialer = { number -> dialedNumber = number },
        )
        val result = command.execute("Mom")
        assertEquals(CommandResult.Launched, result)
        assertEquals("555-1234", dialedNumber)
    }
}
