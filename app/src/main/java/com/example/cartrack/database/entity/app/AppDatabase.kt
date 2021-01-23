package com.example.cartrack.database.entity.app

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.cartrack.database.entity.Country
import com.example.cartrack.database.entity.User
import com.example.cartrack.database.entity.dao.UsersDao

@Database(entities = [User::class, Country::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao() : UsersDao
}