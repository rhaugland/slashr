package com.slashphone.launcher.command

import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class CommandRegistryTest {

    @Test
    fun `registers and retrieves command`() {
        val registry = CommandRegistry(CommandParser())
        val fakeCommand = object : Command {
            override val name = "test"
            override val description = "A test command"
            override suspend fun execute(args: String) = CommandResult.Text("hello")
        }
        registry.register(fakeCommand)
        assertEquals(fakeCommand, registry.get("test"))
    }

    @Test
    fun `returns null for unknown command`() {
        val registry = CommandRegistry(CommandParser())
        assertEquals(null, registry.get("nonexistent"))
    }

    @Test
    fun `execute parses and runs command`() = runTest {
        val registry = CommandRegistry(CommandParser())
        val fakeCommand = object : Command {
            override val name = "greet"
            override val description = "Greets"
            override suspend fun execute(args: String) = CommandResult.Text("hello $args")
        }
        registry.register(fakeCommand)
        val result = registry.execute("/greet World")
        assertEquals(CommandResult.Text("hello World"), result)
    }

    @Test
    fun `execute returns NotFound for unknown command`() = runTest {
        val registry = CommandRegistry(CommandParser())
        val result = registry.execute("/zzz")
        assertTrue(result is CommandResult.NotFound)
    }

    @Test
    fun `suggestions include similar commands`() = runTest {
        val registry = CommandRegistry(CommandParser())
        val fakeCommand = object : Command {
            override val name = "weather"
            override val description = "Weather"
            override suspend fun execute(args: String) = CommandResult.Text("sunny")
        }
        registry.register(fakeCommand)
        val result = registry.execute("/weathr")
        assertEquals(CommandResult.Text("sunny"), result)
    }
}
