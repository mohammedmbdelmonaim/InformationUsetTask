package com.mywork.informationusettask.repository

import androidx.lifecycle.LiveData
import com.mywork.informationusettask.model.entity.User
import com.mywork.informationusettask.utils.Resource

interface UserRepository {
    suspend fun insertUser(user: User)

    suspend fun updateUser(user: User)

    suspend fun deleteUser(user:User)

    suspend fun getUser(phone:String , pass:String): User

    fun getAllUser(): LiveData<List<User>>
}