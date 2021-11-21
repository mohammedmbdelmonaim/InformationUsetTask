package com.mywork.informationusettask.ui.user.proifle

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mywork.informationusettask.model.entity.User
import com.mywork.informationusettask.repository.UserRepositoryImp
import com.mywork.informationusettask.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MyProfileViewModel @Inject constructor(private val repository: UserRepositoryImp): ViewModel() {
    private val editMutableLiveData = MutableLiveData<Resource<User>>()
    val editLiveData: LiveData<Resource<User>> get() = editMutableLiveData

    fun updateUser(user:User) = viewModelScope.launch {
        repository.updateUser(user)
        editMutableLiveData.postValue(Resource.Success(user))
    }

    private val loadMutableLiveData = MutableLiveData<Resource<User>>()
    val loadLiveData: LiveData<Resource<User>> get() = loadMutableLiveData

    fun getUser(phone:String,pass:String) = viewModelScope.launch {
        loadMutableLiveData.postValue(Resource.Success(repository.getUser(phone,pass)))
    }
}