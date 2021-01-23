package com.example.cartrack.repository

import com.example.cartrack.database.entity.Country
import com.example.cartrack.database.entity.User
import com.example.cartrack.database.entity.dao.UsersDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class LoginRepository @Inject constructor(private val usersDao: UsersDao) : LoginRepositoryImpl {

    override suspend fun insertUser() {
        val user = User()
        user.username = "eduardo"
        user.password = "vascodagama31"

        withContext(Dispatchers.IO){
            usersDao.insertUser(user = user)
        }
    }

    override suspend fun insertCountries() {
        val countries = arrayListOf<String>()
        val countriesName = arrayListOf<Country>()

        countries.add("Portugal")
        countries.add("England")
        countries.add("Brazil")
        countries.add("Spain")

        countries.forEach { name ->
            val country = Country()
            country.name = name
            countriesName.add(country)
        }

        withContext(Dispatchers.IO) {
            usersDao.insertCountries(countriesName)
        }
    }

    override suspend fun getCountries() = usersDao.getCountries()
    override suspend fun getUser(username : String) = usersDao.getUser(username)

}