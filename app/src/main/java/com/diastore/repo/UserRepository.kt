package com.diastore.repo

import com.diastore.database.EncryptedUserDao
import com.diastore.model.EncryptedUser
import com.diastore.service.UserService

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

    suspend fun deleteEncryptedUser() {
        encryptedUserDao.deleteAll()
    }
}