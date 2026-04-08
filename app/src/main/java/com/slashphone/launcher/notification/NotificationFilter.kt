package com.slashphone.launcher.notification

enum class FilterDecision {
    PASS_THROUGH,
    CATCH,
}

class NotificationFilter(
    private val whitelistedIdentifiers: Set<String>,
) {
    private val systemPackages = setOf(
        "android",
        "com.android.systemui",
        "com.android.providers.downloads",
    )

    private val urgencyKeywords = listOf(
        "hospital", "emergency", "urgent", "911", "accident",
        "help me", "fire", "ambulance", "dying", "call me now",
    )

    fun decide(packageName: String, title: String, content: String): FilterDecision {
        if (packageName in systemPackages) return FilterDecision.PASS_THROUGH
        if (packageName in whitelistedIdentifiers) return FilterDecision.PASS_THROUGH

        val fullText = "$title $content".lowercase()
        if (urgencyKeywords.any { it in fullText }) return FilterDecision.PASS_THROUGH

        return FilterDecision.CATCH
    }
}
