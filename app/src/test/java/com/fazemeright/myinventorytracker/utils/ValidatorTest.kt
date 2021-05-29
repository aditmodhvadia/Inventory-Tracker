package com.fazemeright.myinventorytracker.utils

import com.fazemeright.myinventorytracker.utils.Validator.isEmailValid
import com.fazemeright.myinventorytracker.utils.Validator.isPasswordValid
import junit.framework.TestCase.assertFalse
import junit.framework.TestCase.assertTrue
import org.junit.Test

class ValidatorTest {

    @Test
    fun `Valid Email Check`() {
        assertTrue("abcd@gmail.com".isEmailValid())
    }

    @Test
    fun `Invalid Email Check`() {
        assertFalse("".isEmailValid())
        assertFalse("abcd@gmailcom".isEmailValid())
        assertFalse("abcd@gmail.".isEmailValid())
        assertFalse("abcdgmail.com".isEmailValid())
    }

    @Test
    fun `Valid Password Check`() {
        assertTrue("1234567".isPasswordValid())
    }

    @Test
    fun `Invalid Password Check`() {
        assertFalse("".isPasswordValid())
        assertFalse("123456".isPasswordValid())
        assertFalse("123".isPasswordValid())
    }
}
