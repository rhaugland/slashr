package com.slashphone.launcher.command

import javax.inject.Inject
import javax.inject.Singleton

data class ParsedCommand(
    val command: String,
    val args: String,
)

@Singleton
class CommandParser @Inject constructor() {

    fun parse(input: String, knownCommands: Set<String> = emptySet()): ParsedCommand {
        val trimmed = input.trim()

        if (!trimmed.startsWith("/")) {
            return ParsedCommand(command = "search", args = trimmed)
        }

        val withoutSlash = trimmed.removePrefix("/")
        val spaceIndex = withoutSlash.indexOf(' ')

        val rawCommand: String
        val args: String

        if (spaceIndex == -1) {
            rawCommand = withoutSlash.lowercase()
            args = ""
        } else {
            rawCommand = withoutSlash.substring(0, spaceIndex).lowercase()
            args = withoutSlash.substring(spaceIndex + 1).trim()
        }

        val resolved = if (knownCommands.isNotEmpty() && rawCommand !in knownCommands) {
            fuzzyMatch(rawCommand, knownCommands) ?: rawCommand
        } else {
            rawCommand
        }

        return ParsedCommand(command = resolved, args = args)
    }

    private fun fuzzyMatch(input: String, candidates: Set<String>): String? {
        val maxDistance = (input.length / 3).coerceAtLeast(1)
        return candidates
            .map { it to levenshtein(input, it) }
            .filter { it.second <= maxDistance }
            .minByOrNull { it.second }
            ?.first
    }

    private fun levenshtein(a: String, b: String): Int {
        val dp = Array(a.length + 1) { IntArray(b.length + 1) }
        for (i in 0..a.length) dp[i][0] = i
        for (j in 0..b.length) dp[0][j] = j
        for (i in 1..a.length) {
            for (j in 1..b.length) {
                val cost = if (a[i - 1] == b[j - 1]) 0 else 1
                dp[i][j] = minOf(
                    dp[i - 1][j] + 1,
                    dp[i][j - 1] + 1,
                    dp[i - 1][j - 1] + cost,
                )
            }
        }
        return dp[a.length][b.length]
    }
}
