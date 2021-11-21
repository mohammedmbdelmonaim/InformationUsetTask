package com.mywork.informationusettask.ui.auth.register

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
class RegisterViewModel @Inject constructor(private val repository: UserRepositoryImp): ViewModel() {
    private val registerMutableLiveData = MutableLiveData<Resource<User>>()
    val registerLiveData: LiveData<Resource<User>> get() = registerMutableLiveData


     fun insertUser(user:User) = viewModelScope.launch {
        repository.insertUser(user)
        registerMutableLiveData.postValue(Resource.Success(user))
    }


}