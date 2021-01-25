package com.example.cartrack.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class User(@PrimaryKey(autoGenerate = true) var id: Int = 0,
                @ColumnInfo(name = "username") var username : String = "",
                @ColumnInfo(name = "password") var password : String = "")