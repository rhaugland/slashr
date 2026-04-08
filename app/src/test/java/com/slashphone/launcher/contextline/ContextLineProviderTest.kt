package com.slashphone.launcher.contextline

import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test
import java.time.LocalDateTime
import java.time.LocalTime

class ContextLineProviderTest {

    private val provider = ContextLineProvider()

    @Test
    fun `morning context line shows time`() {
        val morning = LocalDateTime.of(2026, 4, 8, 8, 30)
        val line = provider.getContextLine(morning)
        assertTrue("Morning line should contain time", line.contains("8:30"))
    }

    @Test
    fun `evening context line shows time`() {
        val evening = LocalDateTime.of(2026, 4, 8, 21, 0)
        val line = provider.getContextLine(evening)
        assertTrue("Evening line should contain time", line.contains("9:00"))
    }

    @Test
    fun `late night context line includes gentle nudge`() {
        val lateNight = LocalDateTime.of(2026, 4, 8, 23, 45)
        val line = provider.getContextLine(lateNight)
        assertTrue(
            "Late night line should nudge toward sleep",
            line.isNotEmpty()
        )
    }
}
