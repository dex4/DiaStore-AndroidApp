package com.diastore.feature.authentication.login

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.diastore.model.EncryptedUser
import com.diastore.model.User
import com.diastore.repo.UserRepository
import com.diastore.util.extensions.combineNonNull
import com.diastore.util.extensions.isValidEmail
import com.diastore.util.extensions.isValidPassword
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class LoginViewModel(private val userRepository: UserRepository) : ViewModel() {

    private val _user = MutableLiveData<User>()
    val user: LiveData<User>
        get() = _user

    private val _error = MutableLiveData<String>()
    val error: LiveData<String>
        get() = _error

    val email = MutableLiveData("")
    val password = MutableLiveData("")

    private val _isLoading = MutableLiveData(false)
    val isLoading: LiveData<Boolean>
        get() = _isLoading

    private val _isLogInEnabled = MediatorLiveData<Boolean>()
    val isLogInEnabled: LiveData<Boolean>
        get() = _isLogInEnabled

    val emailValidator: LiveData<Boolean> = Transformations.map(email) {
        !(it.length >= 4 && !it.isValidEmail())
    }

    val passwordValidator: LiveData<Boolean> = Transformations.map(password) {
        !(it.length >= 4 && !it.isValidPassword())
    }

    init {
        _isLogInEnabled.combineNonNull(email, password, ::validateCredentials)
    }

    private fun validateCredentials(email: String, password: String): Boolean {
        return email.isValidEmail() && password.isValidPassword()
    }

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

            withContext(Dispatchers.Main) {
                try {
                    val user = userRepository.logInUser(email, password)
                    _user.value = user
                    _isLoading.value = false
                } catch (e: Throwable) {
                    Log.e("WRKR", e.toString())
                    _isLoading.value = false
                    _error.value = e.message
                }
            }
        }
    }

    fun saveEncryptedUser(user: EncryptedUser) {
        viewModelScope.launch {
            userRepository.addEncryptedUser(user)
        }
    }
}