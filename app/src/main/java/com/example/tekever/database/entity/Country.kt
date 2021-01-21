package com.example.tekever.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Country(@PrimaryKey(autoGenerate = true) val id: Int = 0,
                   @ColumnInfo(name = "name") var name : String = "")
