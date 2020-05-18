package com.diastore.feature.authentication.signup

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.diastore.model.User
import com.diastore.service.UserService

class SignUpViewModel(private val userService: UserService) : ViewModel() {

    val firstName = MutableLiveData<String>("")
    val lastName = MutableLiveData<String>("")
    val email = MutableLiveData<String>("")
    val password = MutableLiveData<String>("")
    val age = MutableLiveData<String>("")
    val weight = MutableLiveData<String>("")
    val height = MutableLiveData<String>("")
    val carbsToInsulin = MutableLiveData<String>("")
    val bloodSugarToInsulin = MutableLiveData<String>("")

    private val _signUpResponse = MutableLiveData<User>()
    val signUpResponse: LiveData<User>
        get() = _signUpResponse

    private val _isLoading = MutableLiveData<Boolean>(false)
    val isLoading: LiveData<Boolean>
        get() = _isLoading

}