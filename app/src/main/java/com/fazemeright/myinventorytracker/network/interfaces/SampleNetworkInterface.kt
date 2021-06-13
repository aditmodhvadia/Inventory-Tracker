package com.fazemeright.myinventorytracker.network.interfaces

import com.fazemeright.myinventorytracker.network.models.response.User
import retrofit2.http.GET

/**
 * Sample network interface for Retrofit.
 *
 * @author Adit MOdhvadia
 * @since 2.1.1
 */
interface SampleNetworkInterface {

    @GET("users")
    suspend fun getUsers(): User
}
