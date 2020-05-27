package com.diastore.feature.settings

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.diastore.model.User
import com.diastore.repo.EntriesRepository
import com.diastore.repo.UserRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.UUID

class ProfileViewModel(private val userRepository: UserRepository, private val entriesRepository: EntriesRepository) : ViewModel() {

    val firstName = MutableLiveData("")
    val lastName = MutableLiveData("")
    val email = MutableLiveData("")
    val age = MutableLiveData<Int>()
    val weight = MutableLiveData<Int>()
    val height = MutableLiveData<Int>()
    val carbsToInsulinUnit = MutableLiveData<Int>()
    val bloodSugarInsulinUnit = MutableLiveData<Int>()

    private val _isLoading = MutableLiveData(false)
    val isLoading: LiveData<Boolean>
        get() = _isLoading

    private val _user = MutableLiveData<User>()
    val user: LiveData<User>
        get() = _user

    fun updateUser(userId: String) {
        viewModelScope.launch {
            val user = createUser(
                UUID.fromString(userId),
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
                userRepository.updateUser(user)
                _user.value = user
            }
        }
    }

    fun getUser() {
        viewModelScope.launch {
            setUserDetails(userRepository.getUser())
        }
    }

    private fun setUserDetails(user: User) {
        firstName.value = user.firstName
        lastName.value = user.lastName
        email.value = user.email
        height.value = user.height
        weight.value = user.weight
        age.value = user.age
        carbsToInsulinUnit.value = user.carbsToInsulinRatio
        bloodSugarInsulinUnit.value = user.bloodGlucoseToInsulinRatio
    }

    private fun createUser(
        userId: UUID,
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
                userId,
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

    fun clearUserData() {
        CoroutineScope(Dispatchers.IO).launch {
            entriesRepository.deleteAllEntries()
            userRepository.deleteUser()
        }
    }
}