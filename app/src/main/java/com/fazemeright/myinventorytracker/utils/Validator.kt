package com.fazemeright.myinventorytracker.utils

import java.util.regex.Pattern

/**
 * Validation utils extensions
 *
 * @author Adit Modhvadia
 * @since 2.1.1
 */
object Validator {

    fun CharSequence.isEmailValid(): Boolean {
        return !isNullOrEmpty() && EMAIL_ADDRESS.matcher(this).matches()
    }

    fun CharSequence.isPasswordValid(): Boolean {
        return !isNullOrEmpty() && length > 6
    }

    /**
     * Email address pattern
     */
    private val EMAIL_ADDRESS: Pattern = Pattern.compile(
        "[a-zA-Z0-9+._%\\-]{1,256}" +
                "@" +
                "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,64}" +
                "(" +
                "\\." +
                "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,25}" +
                ")+"
    )
}
