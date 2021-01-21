package com.example.tekever.repository


import com.example.tekever.network.UsersInterface
import javax.inject.Inject


class UsersRepository @Inject constructor(private val usersInterface: UsersInterface)
    : UsersRepositoryImpl{

}