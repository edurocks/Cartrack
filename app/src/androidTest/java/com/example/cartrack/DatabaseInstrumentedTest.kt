package com.example.cartrack

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.matcher.ViewMatchers.assertThat
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.cartrack.database.entity.Country
import com.example.cartrack.database.entity.User
import com.example.cartrack.database.entity.app.AppDatabase
import com.example.cartrack.database.entity.dao.UsersDao
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.hamcrest.CoreMatchers.equalTo
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException

@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
class DatabaseInstrumentedTest {

    private lateinit var usersDao : UsersDao

    private lateinit var database: AppDatabase


    @Before
    fun setUp() {
        createDb()
    }

    private fun createDb() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        database = Room.inMemoryDatabaseBuilder(
            context, AppDatabase::class.java
        ).build()
        usersDao = database.userDao()
    }

    @Test
    @Throws(Exception::class)
    fun insertUserNameAndVerifyIfExists() {
        val user = User()
        user.username = "eduardo"

        runBlocking {
          usersDao.insertUser(user)
          val byName = usersDao.getUser("eduardo")
          assertThat(byName.username, equalTo(user.username))
        }
    }


    @Test
    @Throws(Exception::class)
    fun insertUserPasswordAndVerifyIfExists() {
        val user = User()
        user.username = "eduardo"
        user.password = "vascodagama31"

        runBlocking {
            usersDao.insertUser(user)
            val byName = usersDao.getUser("eduardo")
            assertThat(byName.password, equalTo(user.password))
        }
    }

    @Test
    @Throws(Exception::class)
    fun insertUserAndVerifyIfExists() {
        val user = User()
        user.id = 1
        user.username = "eduardo"
        user.password = "vascodagama31"

        runBlocking {
            usersDao.insertUser(user)
            val byName = usersDao.getUser("eduardo")
            assertThat(byName, equalTo(user))
        }
    }

    @Test
    @Throws(Exception::class)
    fun insertCountriesAndVerifyIfNameExists() {
        val listCountries = arrayListOf<Country>()
        val countries = Country()
        countries.name = "England"

        listCountries.add(countries)

        runBlocking {
            usersDao.insertCountries(countries = listCountries)
            val countriesName = usersDao.getCountries()
            assertThat(countriesName[0].name, equalTo("England"))

        }
    }

    @Test
    @Throws(Exception::class)
    fun insertCountriesAndVerifyIfExists() {
        val listCountries = arrayListOf<Country>()
        val countries = Country()
        countries.id = 1
        countries.name = "England"

        listCountries.add(countries)

        runBlocking {
            usersDao.insertCountries(countries = listCountries)
            val countriesName = usersDao.getCountries()
            assertThat(countriesName, equalTo(listCountries))

        }
    }

    @After
    fun tearDown() {
        closeDb()
    }

    @Throws(IOException::class)
    fun closeDb() {
        database.close()
    }
}