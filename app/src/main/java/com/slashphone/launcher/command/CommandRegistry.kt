package com.slashphone.launcher.command

import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CommandRegistry @Inject constructor(
    private val parser: CommandParser,
) {
    private val commands = mutableMapOf<String, Command>()

    fun register(command: Command) {
        commands[command.name] = command
    }

    fun get(name: String): Command? = commands[name]

    fun allNames(): Set<String> = commands.keys.toSet()

    fun allCommands(): List<Command> = commands.values.toList()

    suspend fun execute(input: String): CommandResult {
        val parsed = parser.parse(input, commands.keys)
        val command = commands[parsed.command]
            ?: return CommandResult.NotFound(
                input = parsed.command,
                suggestions = findSuggestions(parsed.command),
            )
        return try {
            command.execute(parsed.args)
        } catch (e: Exception) {
            CommandResult.Error(e.message ?: "Command failed")
        }
    }

    private fun findSuggestions(input: String): List<String> {
        return commands.keys
            .filter { it.startsWith(input.take(2)) }
            .take(3)
    }
}
