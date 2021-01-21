package com.example.tekever.repository

import androidx.lifecycle.LiveData
import com.example.tekever.database.entity.Country
import com.example.tekever.database.entity.User

interface LoginRepositoryImpl {

    suspend fun insertUser()
    suspend fun insertCountries()
    fun getCountries() : LiveData<List<Country>>
    suspend fun getUser(username : String) : User

}