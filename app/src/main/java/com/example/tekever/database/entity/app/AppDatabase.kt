package com.example.tekever.database.entity.app

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.tekever.database.entity.Country
import com.example.tekever.database.entity.User
import com.example.tekever.database.entity.dao.UsersDao

@Database(entities = [User::class, Country::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao() : UsersDao
}