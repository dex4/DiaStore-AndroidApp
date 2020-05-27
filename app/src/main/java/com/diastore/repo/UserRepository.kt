package com.diastore.repo

import com.diastore.database.UserDao
import com.diastore.model.User
import com.diastore.service.UserService

class UserRepository(
    private val userDao: UserDao,
    private val userService: UserService
) {
    suspend fun addUser(user: User) {
        userDao.insert(user)
    }

    suspend fun updateUser(user: User) {
        userDao.update(user)
    }

    suspend fun getUser() = userDao.getUser()[0]

    suspend fun deleteUser() {
        userDao.deleteAll()
    }
}