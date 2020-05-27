package com.diastore.feature.authentication.signup

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.diastore.model.User
import com.diastore.repo.UserRepository
import kotlinx.coroutines.launch
import java.util.UUID

class SignUpViewModel(private val userRepository: UserRepository) : ViewModel() {

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

    private val _user = MutableLiveData<User>()
    val user: LiveData<User>
        get() = _user

    private fun createUser(
        firstName: String?,
        lastName: String?,
        email: String?,
        age: Int?,
        weight: Int?,
        height: Int?,
        carbsToInsulinUnit: Int?,
        bloodSugarInsulinUnit: Int?
    ): User? =
        if (firstName == null || lastName == null || email == null || age == null ||
            weight == null || height == null || carbsToInsulinUnit == null || bloodSugarInsulinUnit == null
        ) {
            null
        } else {
            User(
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


    fun addUser() {
        viewModelScope.launch {
            val user = createUser(
                firstName.value,
                lastName.value,
                email.value,
                age.value,
                weight.value,
                height.value,
                carbsToInsulinUnit.value,
                bloodSugarInsulinUnit.value
            )
            user?.let {
                userRepository.addUser(user)
                _user.value = user
            }
        }
    }

}