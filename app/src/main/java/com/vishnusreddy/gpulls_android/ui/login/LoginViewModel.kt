package com.vishnusreddy.gpulls_android.ui.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vishnusreddy.gpulls_android.R
import com.vishnusreddy.gpulls_android.data.repository.AppNetworkRepository
import com.vishnusreddy.gpulls_android.utils.network.NetworkTools
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okio.IOException
import retrofit2.HttpException

class LoginViewModel : ViewModel() {

    private val repository: AppNetworkRepository = AppNetworkRepository(NetworkTools.githubAPI)

    private val _userNameValidMutableLiveData: MutableLiveData<Boolean> = MutableLiveData()
    val userNameValidLiveDate: LiveData<Boolean> get() = _userNameValidMutableLiveData

    private val _showMessageInSnackbar: MutableLiveData<Int> = MutableLiveData()
    val showMessageInSnackbar: LiveData<Int> get() = _showMessageInSnackbar

    fun checkIfUserExists(userName: String) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = repository.getUserInfo(userName)
                if (response.isSuccessful) {
                    _userNameValidMutableLiveData.postValue(true)
                } else {
                    _userNameValidMutableLiveData.postValue(false)
                }
            } catch (exception: IOException) {
                _showMessageInSnackbar.postValue(R.string.please_check_your_internet_and_try_again)
            } catch (exception: HttpException) {
                _showMessageInSnackbar.postValue(R.string.please_check_your_internet_and_try_again)
            }
        }
    }
}