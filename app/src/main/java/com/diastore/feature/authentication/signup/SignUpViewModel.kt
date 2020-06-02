package com.diastore.feature.authentication.signup

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

    private val _user = MutableLiveData<User>()
    val user: LiveData<User>
        get() = _user

    private val _signUpResponse = MutableLiveData<User>()
    val signUpResponse: LiveData<User>
        get() = _signUpResponse

    private val _isLoading = MutableLiveData(false)
    val isLoading: LiveData<Boolean>
        get() = _isLoading

    private val _isSignUpEnabled = MediatorLiveData<Boolean>()
    val isSignUpEnabled: LiveData<Boolean>
        get() = _isSignUpEnabled

    private val _isConfirmProfileEnabled = MediatorLiveData<Boolean>()
    val isConfirmProfileEnabled: LiveData<Boolean>
        get() = _isConfirmProfileEnabled

    val emailValidator: LiveData<Boolean> = Transformations.map(email) {
        !(it.length >= 4 && !it.isValidEmail())
    }

    val passwordValidator: LiveData<Boolean> = Transformations.map(password) {
        !(it.length >= 4 && !it.isValidPassword())
    }

    init {
        _isSignUpEnabled.combineNonNull(email, password, firstName, lastName, ::validateCredentials)
        _isConfirmProfileEnabled.combineNonNull(age, weight, height, carbsToInsulinUnit, bloodSugarInsulinUnit, ::validateProfile)
    }

    private fun validateCredentials(email: String, password: String, firstName: String, lastName: String): Boolean {
        return email.isValidEmail() && password.isValidPassword() && firstName.isNotEmpty() && lastName.isNotEmpty()
    }

    private fun validateProfile(age: Int, weight: Int, height: Int, carbsToInsulinUnit: Int, bloodSugarInsulinUnit: Int): Boolean {
        return age != 0 && weight != 0 && height != 0 && carbsToInsulinUnit != 0 && bloodSugarInsulinUnit != 0
    }

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

    fun getUser() = createUser(
        firstName.value,
        lastName.value,
        email.value,
        age.value,
        weight.value,
        height.value,
        carbsToInsulinUnit.value,
        bloodSugarInsulinUnit.value
    )

    fun saveEncryptedUser(user: EncryptedUser) {
        viewModelScope.launch {
            userRepository.addEncryptedUser(user)
        }
    }

}