package com.mywork.informationusettask.model.locale

import androidx.lifecycle.LiveData
import androidx.room.*
import com.mywork.informationusettask.model.entity.User
import com.mywork.informationusettask.utils.Resource
import kotlinx.coroutines.flow.Flow


@Dao
interface UserDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUser(user:User)

    @Update
    suspend fun updateUser(user:User)

    @Delete
    suspend fun deleteUser(user:User)

    @Query("SELECT * FROM users")
    fun getAllUser(): LiveData<List<User>>

    @Query("SELECT * FROM users WHERE phone = :phone AND password = :pass")
    suspend fun getUser(phone:String , pass:String): User

}