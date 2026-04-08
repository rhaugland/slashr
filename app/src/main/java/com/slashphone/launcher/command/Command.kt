package com.slashphone.launcher.command

interface Command {
    val name: String
    val description: String
    suspend fun execute(args: String): CommandResult
}
