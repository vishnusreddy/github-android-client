package com.vishnusreddy.gpulls_android.ui.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vishnusreddy.gpulls_android.data.repository.AppNetworkRepository
import com.vishnusreddy.gpulls_android.utils.network.NetworkTools
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class LoginViewModel : ViewModel() {

    private val repository: AppNetworkRepository = AppNetworkRepository(NetworkTools.githubAPI)

    private val _userNameValidMutableLiveData: MutableLiveData<Boolean> = MutableLiveData()
    val userNameValidLiveDate: LiveData<Boolean> get() = _userNameValidMutableLiveData

    fun checkIfUserExists(userName: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val response = repository.getUserInfo(userName)
            if (response.isSuccessful) {
                _userNameValidMutableLiveData.postValue(true)
            } else {
                _userNameValidMutableLiveData.postValue(false)
            }
        }
    }
}