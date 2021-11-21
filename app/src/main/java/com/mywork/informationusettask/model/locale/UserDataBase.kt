package com.mywork.informationusettask.model.locale

import androidx.room.Database
import androidx.room.RoomDatabase
import com.mywork.informationusettask.model.entity.User

@Database(entities = [User::class] , version = 1 , exportSchema = false)//exportSchema->make many versions in history
abstract class UserDataBase : RoomDatabase(){
    abstract fun userDao(): UserDao
}