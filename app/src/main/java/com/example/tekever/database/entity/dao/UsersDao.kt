package com.example.tekever.database.entity.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.tekever.database.entity.Country
import com.example.tekever.database.entity.User

@Dao
interface UsersDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUser(user : User)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCountries(countries : List<Country>)

    @Query("SELECT * FROM country")
    fun getCountries(): LiveData<List<Country>>

    @Query("SELECT * FROM user WHERE username = :username")
    suspend fun getUser(username : String): User
}