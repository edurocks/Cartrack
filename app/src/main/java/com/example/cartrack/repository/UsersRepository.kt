package com.example.cartrack.repository


import androidx.lifecycle.MutableLiveData
import com.example.cartrack.model.UsersResponse
import com.example.cartrack.network.UsersInterface
import javax.inject.Inject


class UsersRepository @Inject constructor(private val usersInterface: UsersInterface)
    : UsersRepositoryImpl{

    val mUsers: MutableLiveData<Resource<List<UsersResponse>>>
            = MutableLiveData<Resource<List<UsersResponse>>>()

    override suspend fun getUsers() = usersInterface.getUsers()


    /* override fun getUsers() {
         usersInterface.getUsers().enqueue(object : Callback<List<UsersResponse>>{
             override fun onResponse(call: Call<List<UsersResponse>>, response: Response<List<UsersResponse>>) {
                 if (response.isSuccessful && response.code() == HttpsURLConnection.HTTP_OK){
                     mUsers.value = Resource.success(response.body()!!)
                 }
             }

             override fun onFailure(call: Call<List<UsersResponse>>, t: Throwable) {
                 mUsers.value = Resource.error(null, t.message!!)
             }
         })
     } */
}