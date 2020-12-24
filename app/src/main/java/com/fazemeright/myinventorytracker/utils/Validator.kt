package com.fazemeright.myinventorytracker.utils

import java.util.regex.Pattern

object Validator {

    fun CharSequence.isEmailValid(): Boolean {
        return !isNullOrEmpty() && EMAIL_ADDRESS.matcher(this).matches()
    }

    fun CharSequence.isPasswordValid(): Boolean {
        return !isNullOrEmpty() && length > 6
    }

    private val EMAIL_ADDRESS: Pattern = Pattern.compile(
        "[a-zA-Z0-9\\+\\.\\_\\%\\-\\+]{1,256}" +
                "\\@" +
                "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,64}" +
                "(" +
                "\\." +
                "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,25}" +
                ")+"
    )
}

