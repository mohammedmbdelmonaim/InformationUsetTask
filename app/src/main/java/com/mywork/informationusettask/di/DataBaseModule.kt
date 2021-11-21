package com.mywork.informationusettask.di

import android.app.Application
import androidx.room.Room
import com.mywork.informationusettask.model.locale.UserDao
import com.mywork.informationusettask.model.locale.UserDataBase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
class DataBaseModule {
    @Provides
    @Singleton
    fun provideDB(application: Application?): UserDataBase {
        return Room.databaseBuilder(application!!, UserDataBase::class.java, "user_db")
            .fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    @Singleton
    fun provideDao(userDataBase: UserDataBase): UserDao {
        return userDataBase.userDao()
    }
}