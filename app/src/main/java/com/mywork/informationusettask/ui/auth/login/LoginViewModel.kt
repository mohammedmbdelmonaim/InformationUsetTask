package com.mywork.informationusettask.ui.auth.login

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
class LoginViewModel @Inject constructor(private val repository: UserRepositoryImp): ViewModel() {
    private val loginMutableLiveData = MutableLiveData<Resource<User>>()
    val loginLiveData: LiveData<Resource<User>> get() = loginMutableLiveData

    fun getUser(phone:String,pass:String) = viewModelScope.launch {
        loginMutableLiveData.postValue(Resource.Success(repository.getUser(phone,pass)))
    }
}