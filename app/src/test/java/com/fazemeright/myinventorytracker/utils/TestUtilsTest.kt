package com.fazemeright.myinventorytracker.utils

import org.junit.Assert.assertTrue
import org.junit.Test
import java.util.regex.Pattern

class TestUtilsTest {
    @Test
    fun getRandomColorMatchesRegex() {
        assertTrue(Pattern.matches("#[0-9a-fA-F]{6}", TestUtils.getRandomColor()))
    }
}