package com.diastore.feature.authentication.login

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.diastore.model.User
import com.diastore.service.UserService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class LoginViewModel(private val userService: UserService) : ViewModel() {
    private val _user = MutableLiveData<User>()
    val user: LiveData<User>
        get() = _user

    val email = MutableLiveData<String>("")
    val password = MutableLiveData<String>("")

    private val _isLoading = MutableLiveData<Boolean>(false)
    val isLoading: LiveData<Boolean>
        get() = _isLoading

    fun login() {
        _isLoading.value = true
        CoroutineScope(Dispatchers.IO).launch {
            var email = ""
            var password = ""
            this@LoginViewModel.email.value?.let {
                email = it
            }
            this@LoginViewModel.password.value?.let {
                password = it
            }
            val request = userService.login(email, password)

            withContext(Dispatchers.Main) {
                try {
                    _user.value = request.await()
                    _isLoading.value = false
                } catch (e: Throwable) {
                    Log.e("WRKR", e.toString())
                    _isLoading.value = false
//                    _getError.value = e.message
                }
            }
        }
    }
}