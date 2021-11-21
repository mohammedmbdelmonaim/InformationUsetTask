package com.mywork.informationusettask.ui.user.all

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mywork.informationusettask.model.entity.User
import com.mywork.informationusettask.model.locale.UserDao
import com.mywork.informationusettask.repository.UserRepository
import com.mywork.informationusettask.repository.UserRepositoryImp
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AllUsersViewModel @Inject constructor( private val repository:UserRepositoryImp): ViewModel() {
    private val deleteMutableLiveData = MutableLiveData<Boolean>()
    val deleteLiveData:LiveData<Boolean> get() = deleteMutableLiveData

    fun getUsers() = repository.getAllUser()

    fun deleteUser(user:User){
        viewModelScope.launch {
            repository.deleteUser(user)
            deleteMutableLiveData.postValue(true)
        }
    }

}