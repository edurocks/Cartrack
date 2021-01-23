package com.example.cartrack.repository

import com.example.cartrack.database.entity.Country
import com.example.cartrack.database.entity.User

interface LoginRepositoryImpl {

    suspend fun insertUser()
    suspend fun insertCountries()
    suspend fun getCountries() : List<Country>
    suspend fun getUser(username : String) : User

}