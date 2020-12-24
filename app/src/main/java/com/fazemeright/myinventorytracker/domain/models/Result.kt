package com.fazemeright.myinventorytracker.domain.models


sealed class Result<out T : Any> {

    data class Success<out T : Any>(val data: T, val msg: String = "") : Result<T>()
    data class Error(val exception: Exception? = null, val msg: String) : Result<Nothing>()

    override fun toString(): String {
        return when (this) {
            is Success<*> -> "Success[data=$data, msg=$msg]"
            is Error -> "Error[exception=$exception, msg=$msg]"
        }
    }
}