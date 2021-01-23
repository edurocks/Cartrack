package com.example.cartrack.ui.main

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cartrack.database.entity.Country
import com.example.cartrack.database.entity.User
import com.example.cartrack.model.UsersResponse
import com.example.cartrack.repository.LoginRepository
import com.example.cartrack.repository.Resource
import com.example.cartrack.repository.UsersRepository
import kotlinx.coroutines.*

@Suppress("UNCHECKED_CAST")
class UsersViewModel @ViewModelInject constructor(private val usersRepository: UsersRepository,
                                                  private val loginRepository: LoginRepository)
                    : ViewModel() {

    private val _mUser = MutableLiveData<User>()
    val mUser : LiveData<User>
    get() {
        return _mUser
    }

    private val _mCountriesName = MutableLiveData<List<Country>>()
    val mCountriesName : LiveData<List<Country>>
        get() {
            return _mCountriesName
        }

    private var _mErrorMessage: MutableLiveData<String> = MutableLiveData()

    val mErrorMessage : LiveData<String>
        get() {
          return _mErrorMessage
        }

    private val _mUsersResponse = MutableLiveData<Resource<List<UsersResponse>>>()

    val mUsersResponse: LiveData<Resource<List<UsersResponse>>>
    get() {
        return _mUsersResponse
    }

    fun insertUser(){
        viewModelScope.launch {
            loginRepository.insertUser()
        }
    }

    fun insertCountry(){
        viewModelScope.launch {
            loginRepository.insertCountries()
            getCountries()
        }
    }

    private fun getCountries(){
        viewModelScope.launch {
            _mCountriesName.value = loginRepository.getCountries()
        }
    }

    fun getAndValidateUser(username : String, password : String, country : String) {
        if (username.isBlank() || username.isEmpty()){
            _mErrorMessage.value = "Username cannot be empty"
        }
        if (password.isBlank() || password.isEmpty()){
            _mErrorMessage.value = "Password cannot be empty"
        }
        if (country.isBlank() || country.isEmpty()){
            _mErrorMessage.value = "Country cannot be empty"
        }
        if(username.isNotBlank() && username.isNotEmpty() &&
           password.isNotBlank() && password.isNotEmpty() &&
           country.isNotBlank()  && country.isNotEmpty()){
            viewModelScope.launch {
                _mUser.value = loginRepository.getUser(username)
            }
        }
    }

    fun getUsers(){
        viewModelScope.launch {
            try {
                _mUsersResponse.value = Resource.success(usersRepository.getUsers())
            }catch (exception : Exception){
                _mUsersResponse.value = Resource.error(null, exception.message!!)
            }
        }
    }
}