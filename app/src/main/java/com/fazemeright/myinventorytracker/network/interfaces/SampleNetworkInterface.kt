package com.fazemeright.myinventorytracker.network.interfaces

import com.fazemeright.myinventorytracker.network.models.response.User
import retrofit2.http.GET

interface SampleNetworkInterface {

    @GET("users")
    suspend fun getUsers(): User
}