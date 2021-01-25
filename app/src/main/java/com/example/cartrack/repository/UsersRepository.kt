package com.example.cartrack.repository


import com.example.cartrack.network.UsersInterface
import javax.inject.Inject


class UsersRepository @Inject constructor(private val usersInterface: UsersInterface)
    : UsersRepositoryImpl{

    override suspend fun getUsers() = usersInterface.getUsers()
}