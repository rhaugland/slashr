package com.slashphone.launcher.command

import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Test

class CommandParserTest {

    private val parser = CommandParser()

    @Test
    fun `parses slash command with no args`() {
        val result = parser.parse("/weather")
        assertEquals("weather", result.command)
        assertEquals("", result.args)
    }

    @Test
    fun `parses slash command with args`() {
        val result = parser.parse("/call Mom")
        assertEquals("call", result.command)
        assertEquals("Mom", result.args)
    }

    @Test
    fun `parses slash command with multi-word args`() {
        val result = parser.parse("/go 123 Main Street")
        assertEquals("go", result.command)
        assertEquals("123 Main Street", result.args)
    }

    @Test
    fun `trims whitespace`() {
        val result = parser.parse("  /weather  ")
        assertEquals("weather", result.command)
        assertEquals("", result.args)
    }

    @Test
    fun `input without slash is treated as search`() {
        val result = parser.parse("best restaurants nearby")
        assertEquals("search", result.command)
        assertEquals("best restaurants nearby", result.args)
    }

    @Test
    fun `case insensitive command name`() {
        val result = parser.parse("/WEATHER")
        assertEquals("weather", result.command)
    }

    @Test
    fun `fuzzy matches close commands`() {
        val knownCommands = setOf("weather", "call", "text", "today", "tomorrow", "search", "camera")
        val result = parser.parse("/weathr", knownCommands)
        assertEquals("weather", result.command)
    }

    @Test
    fun `no fuzzy match for distant input`() {
        val knownCommands = setOf("weather", "call", "text")
        val result = parser.parse("/zzzzz", knownCommands)
        assertEquals("zzzzz", result.command)
    }
}
