package com.diastore.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.diastore.model.EncryptedUser

@Dao
interface EncryptedUserDao {
    @Insert
    suspend fun insert(encryptedUser: EncryptedUser)

    @Update
    suspend fun update(encryptedUser: EncryptedUser)

    @Query("select * from encryptedUser")
    suspend fun getEncryptedUser(): List<EncryptedUser>

    @Query("delete from encryptedUser")
    suspend fun deleteAll()
}