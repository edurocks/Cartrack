package com.example.tekever.ui.main

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tekever.database.entity.Country
import com.example.tekever.database.entity.User
import com.example.tekever.repository.LoginRepository
import com.example.tekever.repository.UsersRepository
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

    var mCountriesName: LiveData<List<Country>>? = null
    var _mErrorMessage: MutableLiveData<String> = MutableLiveData()

    val mErrorMessage : LiveData<String>
        get() {
          return _mErrorMessage
        }

    fun insertUser(){
        viewModelScope.launch {
            loginRepository.insertUser()
        }
    }

    fun insertCountry(){
        viewModelScope.launch {
            loginRepository.insertCountries()
        }

        getCountries()
    }

    private fun getCountries(){
        mCountriesName = loginRepository.getCountries()
    }

    fun getAndValidateUser(username : String, password : String, country : String) {
        if (username.isBlank() || username.isEmpty()){
            _mErrorMessage.value = "Username cannot be empty"
        }else if (password.isBlank() || password.isEmpty()){
            _mErrorMessage.value = "Password cannot be empty"
        }else if (country.isBlank() || country.isEmpty()){
            _mErrorMessage.value = "Country cannot be empty"
        }else{
            viewModelScope.launch {
                _mUser.value = loginRepository.getUser(username)
            }
        }
    }
}