package com.fazemeright.myinventorytracker.domain.models

/**
 * Store result of any api call as either [Success] or [Error].
 * Use when block with smart cast to handle the [Result]
 *
 * @author Adit Modhvadia
 * @since 2.1.1
 */
sealed class Result<out T : Any> {
    /**
     * Signifies a successful [Result] for an api call.
     *
     * @author Adit Modhvadia
     * @since 2.1.1
     */
    data class Success<out T : Any>(val data: T, val msg: String = "") : Result<T>()

    /**
     * Signifies an error [Result] for an api call.
     *
     * @author Adit Modhvadia
     * @since 2.1.1
     */
    data class Error(val exception: Exception? = null, val msg: String) : Result<Nothing>()

    override fun toString(): String {
        return when (this) {
            is Success<*> -> "Success[data=$data, msg=$msg]"
            is Error -> "Error[exception=$exception, msg=$msg]"
        }
    }
}
