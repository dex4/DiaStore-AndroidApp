package com.diastore.feature.settings

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.diastore.model.EncryptedUser
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

    private val _encryptedUser = MutableLiveData<EncryptedUser>()
    val encryptedUser: LiveData<EncryptedUser>
        get() = _encryptedUser

    fun getUser(userId: UUID) = createUser(
        userId,
        firstName.value,
        lastName.value,
        email.value,
        age.value,
        weight.value,
        height.value,
        carbsToInsulinUnit.value,
        bloodSugarInsulinUnit.value
    )

    fun getEncryptedUser() {
        viewModelScope.launch {
            _encryptedUser.value = userRepository.getEncryptedUser()
        }
    }

    fun saveEncryptedUser(user: EncryptedUser) {
        viewModelScope.launch {
            _encryptedUser.value = user
            userRepository.updateEncryptedUser(user)
        }
    }

    fun clearPreviousAuthenticationData() {
        email.value = ""
        age.value = null
        weight.value = null
        height.value = null
        carbsToInsulinUnit.value = null
        bloodSugarInsulinUnit.value = null
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
//            entriesRepository.deleteAllEntries()
            userRepository.deleteEncryptedUser()
        }
    }
}