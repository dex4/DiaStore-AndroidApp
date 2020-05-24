package com.diastore.feature.authentication.signup

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.diastore.model.User
import com.diastore.repo.UserRepo
import com.diastore.util.extensions.combineNonNull
import kotlinx.coroutines.launch
import java.util.UUID

class SignUpViewModel(private val userRepo: UserRepo) : ViewModel() {

    val firstName = MutableLiveData("")
    val lastName = MutableLiveData("")
    val email = MutableLiveData("")
    val password = MutableLiveData("")
    val age = MutableLiveData<Int>()
    val weight = MutableLiveData<Int>()
    val height = MutableLiveData<Int>()
    val carbsToInsulinUnit = MutableLiveData<Int>()
    val bloodSugarInsulinUnit = MutableLiveData<Int>()

    private val _signUpResponse = MutableLiveData<User>()
    val signUpResponse: LiveData<User>
        get() = _signUpResponse

    private val _isLoading = MutableLiveData(false)
    val isLoading: LiveData<Boolean>
        get() = _isLoading

    val user = MediatorLiveData<User>()

    init {
        user.combineNonNull(
            firstName, lastName, email, password, age, weight, height, carbsToInsulinUnit, bloodSugarInsulinUnit
        ) { firstName, lastName, email, _, age, weight, height, carbsToInsulinUnit, bloodSugarInsulinUnit ->
            return@combineNonNull User(
                UUID.randomUUID(),
                firstName,
                lastName,
                email,
                "",
                weight,
                height,
                carbsToInsulinUnit,
                bloodSugarInsulinUnit,
                age
            )
        }
    }

    fun addUser() {
        viewModelScope.launch {
            user.value?.let {
                userRepo.addUser(it)
            }
        }
    }

}