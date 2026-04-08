package com.slashphone.launcher.notification

import org.junit.Assert.assertEquals
import org.junit.Test

class NotificationFilterTest {

    @Test
    fun `whitelisted package passes through`() {
        val filter = NotificationFilter(
            whitelistedIdentifiers = setOf("com.whatsapp"),
        )
        val decision = filter.decide(
            packageName = "com.whatsapp",
            title = "Mom",
            content = "hey",
        )
        assertEquals(FilterDecision.PASS_THROUGH, decision)
    }

    @Test
    fun `non-whitelisted package is caught`() {
        val filter = NotificationFilter(
            whitelistedIdentifiers = setOf("com.whatsapp"),
        )
        val decision = filter.decide(
            packageName = "com.twitter.android",
            title = "Trending",
            content = "Something is trending",
        )
        assertEquals(FilterDecision.CATCH, decision)
    }

    @Test
    fun `urgent content passes through even if not whitelisted`() {
        val filter = NotificationFilter(
            whitelistedIdentifiers = emptySet(),
        )
        val decision = filter.decide(
            packageName = "com.google.android.apps.messaging",
            title = "John",
            content = "I'm at the hospital",
        )
        assertEquals(FilterDecision.PASS_THROUGH, decision)
    }

    @Test
    fun `system packages always pass through`() {
        val filter = NotificationFilter(
            whitelistedIdentifiers = emptySet(),
        )
        val decision = filter.decide(
            packageName = "android",
            title = "System",
            content = "Low battery",
        )
        assertEquals(FilterDecision.PASS_THROUGH, decision)
    }
}
