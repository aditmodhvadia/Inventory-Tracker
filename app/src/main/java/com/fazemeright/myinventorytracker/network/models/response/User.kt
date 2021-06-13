package com.fazemeright.myinventorytracker.network.models.response

/**
 * Holds user data.
 *
 * @author Adit Modhvadia
 * @since 2.1.1
 */
@Suppress("unused")
data class User(
    val id: String,
    var firstName: String,
    var lastName: String
)
