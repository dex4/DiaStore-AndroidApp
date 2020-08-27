package com.diastore.feature.profile

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.diastore.model.EncryptedUser
import com.diastore.model.User
import com.diastore.model.dto.UpdateUserInfoDTO
import com.diastore.repo.EntriesRepository
import com.diastore.repo.UserRepository
import com.diastore.util.EncryptionUtils
import com.diastore.util.extensions.encryptUser
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
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

    private val _deletedData = MutableLiveData(-1)
    val deletedData: LiveData<Int>
        get() = _deletedData

    private val _error = MutableLiveData<String>()
    val error: LiveData<String>
        get() = _error

    private val _isLoading = MutableLiveData(false)
    val isLoading: LiveData<Boolean>
        get() = _isLoading

    fun updateUser(userId: UUID) {
        _isLoading.value = true
        CoroutineScope(Dispatchers.IO).launch {
            withContext(Dispatchers.Main) {
                try {
                    getUser(userId)?.let { updatedUser ->
                        saveEncryptedUser(
                            userRepository.updateUser(
                                userId,
                                UpdateUserInfoDTO(
                                    userId,
                                    updatedUser.weight,
                                    updatedUser.height,
                                    updatedUser.carbsToInsulinRatio,
                                    updatedUser.bloodGlucoseToInsulinRatio,
                                    updatedUser.age
                                )
                            ).encryptUser()
                        )
                    }
                    _isLoading.value = false
                } catch (e: Throwable) {
                    Log.e("WRKR", e.toString())
                    _isLoading.value = false
                    _error.value = e.message
                }
            }
        }
    }

    private fun getUser(userId: UUID) = createUser(
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
        CoroutineScope(Dispatchers.IO).launch {
            withContext(Dispatchers.Main) {
                userRepository.getEncryptedUser().let {
                    email.value = EncryptionUtils.decrypt(it.email)
                    age.value = EncryptionUtils.decrypt(it.age).toInt()
                    bloodSugarInsulinUnit.value = EncryptionUtils.decrypt(it.bloodGlucoseToInsulinRatio).toInt()
                    carbsToInsulinUnit.value = EncryptionUtils.decrypt(it.carbsToInsulinRatio).toInt()
                    weight.value = EncryptionUtils.decrypt(it.weight).toInt()
                    height.value = EncryptionUtils.decrypt(it.height).toInt()
                }
            }
        }
    }

    private suspend fun saveEncryptedUser(user: EncryptedUser) {
        userRepository.updateEncryptedUser(user)
    }

    fun clearPreviousAuthenticationData() {
        email.value = ""
        _deletedData.value = -1
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
            withContext(Dispatchers.Main) {
//            entriesRepository.deleteAllEntries()
                _deletedData.value = userRepository.deleteEncryptedUser()
            }
        }
    }
}