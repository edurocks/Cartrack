package com.example.tekever.di

import android.content.Context
import androidx.room.Room
import com.example.tekever.database.entity.app.AppDatabase
import com.example.tekever.database.entity.dao.UsersDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext applicationContext: Context) : AppDatabase{
        return Room.databaseBuilder(applicationContext,
                AppDatabase::class.java,
            "cartrack")
               .build()
    }

    @Provides
    fun provideUserDao(appDatabase: AppDatabase) : UsersDao{
        return appDatabase.userDao()
    }
}