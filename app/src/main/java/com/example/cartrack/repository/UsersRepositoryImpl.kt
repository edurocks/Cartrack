package com.example.cartrack.repository

import com.example.cartrack.model.UsersResponse


interface UsersRepositoryImpl {
    suspend fun getUsers() : List<UsersResponse>
}