package com.slashphone.launcher.command.builtin

import com.slashphone.launcher.command.Command
import com.slashphone.launcher.command.CommandResult

class CameraCommand(
    private val openCamera: suspend () -> Unit,
) : Command {
    override val name = "camera"
    override val description = "Open the camera"

    override suspend fun execute(args: String): CommandResult {
        openCamera()
        return CommandResult.Launched
    }
}
