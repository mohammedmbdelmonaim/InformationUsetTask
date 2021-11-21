package com.mywork.informationusettask.repository

import androidx.lifecycle.LiveData
import com.mywork.informationusettask.model.entity.User
import com.mywork.informationusettask.model.locale.UserDao
import com.mywork.informationusettask.model.locale.UserDataBase
import com.mywork.informationusettask.utils.Resource
import javax.inject.Inject

class UserRepositoryImp @Inject constructor(private val userDao: UserDao): UserRepository {
    override suspend fun insertUser(user: User) {
        userDao.insertUser(user)
    }

    override suspend fun updateUser(user: User) {
        userDao.updateUser(user)
    }

    override suspend fun deleteUser(user: User) {
        userDao.deleteUser(user)
    }

    override suspend fun getUser(phone: String , pass:String): User {
       return userDao.getUser(phone,pass)
    }

    override fun getAllUser(): LiveData<List<User>> {
        return userDao.getAllUser()
    }


}