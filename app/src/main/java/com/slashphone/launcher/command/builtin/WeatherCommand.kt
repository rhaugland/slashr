package com.slashphone.launcher.command.builtin

import com.slashphone.launcher.command.Command
import com.slashphone.launcher.command.CommandResult

class WeatherCommand(
    private val getWeather: suspend () -> String,
) : Command {
    override val name = "weather"
    override val description = "Current conditions + forecast"

    override suspend fun execute(args: String): CommandResult {
        return CommandResult.Text(getWeather())
    }
}
