package com.slashphone.launcher.command.builtin

import com.slashphone.launcher.command.Command
import com.slashphone.launcher.command.CommandResult

class MusicCommand(
    private val openMusicPlayer: suspend () -> Unit,
) : Command {
    override val name = "music"
    override val description = "Open music player"

    override suspend fun execute(args: String): CommandResult {
        openMusicPlayer()
        return CommandResult.Launched
    }
}
