package com.example.cartrack.network

import com.example.cartrack.model.UsersResponse
import retrofit2.Call
import retrofit2.http.GET

interface UsersInterface {

    @GET("users")
    suspend fun getUsers() : List<UsersResponse>
}