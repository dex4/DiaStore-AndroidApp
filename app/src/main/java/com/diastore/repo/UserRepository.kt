package com.diastore.repo

import com.diastore.database.EncryptedUserDao
import com.diastore.model.EncryptedUser
import com.diastore.model.User
import com.diastore.model.dto.UpdateUserInfoDTO
import com.diastore.model.dto.UserLogInDTO
import com.diastore.model.dto.UserSignUpDTO
import com.diastore.service.UserService
import java.util.UUID

class UserRepository(
    private val encryptedUserDao: EncryptedUserDao,
    private val userService: UserService
) {

    suspend fun addEncryptedUser(user: EncryptedUser) {
        encryptedUserDao.insert(user)
    }

    suspend fun updateEncryptedUser(user: EncryptedUser) {
        encryptedUserDao.update(user)
    }

    suspend fun getEncryptedUser() = encryptedUserDao.getEncryptedUser()[0]

    suspend fun deleteEncryptedUser() =
        encryptedUserDao.deleteAll()


    suspend fun logInUser(email: String, password: String): User = userService.loginUser(UserLogInDTO(email, password))

    suspend fun updateUser(userId: UUID, updateUserInfoDto: UpdateUserInfoDTO): User = userService.updateUser(userId, updateUserInfoDto)

    suspend fun signUpUser(
        firstName: String,
        lastName: String,
        email: String,
        password: String,
        weight: Int,
        height: Int,
        carbsToInsulinUnit: Int,
        bloodSugarInsulinUnit: Int,
        age: Int
    ): User = userService.signUpUser(
        UserSignUpDTO(
            firstName,
            lastName,
            email,
            password,
            weight,
            height,
            carbsToInsulinUnit,
            bloodSugarInsulinUnit,
            age
        )
    )
}