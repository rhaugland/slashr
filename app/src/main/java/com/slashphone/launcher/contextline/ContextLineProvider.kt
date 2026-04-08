package com.slashphone.launcher.contextline

import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ContextLineProvider @Inject constructor() {

    private val timeFormat = DateTimeFormatter.ofPattern("h:mm a")

    fun getContextLine(now: LocalDateTime = LocalDateTime.now()): String {
        val time = now.format(timeFormat)
        val hour = now.hour

        return when {
            hour in 5..8 -> "$time — good morning"
            hour in 9..11 -> time
            hour in 12..13 -> "$time — afternoon"
            hour in 14..17 -> time
            hour in 18..21 -> "$time — evening"
            hour in 22..23 -> "$time — wind down"
            else -> "$time — you should be sleeping"
        }
    }
}
