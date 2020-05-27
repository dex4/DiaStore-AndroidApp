package com.diastore.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.diastore.model.User

@Dao
interface UserDao {
    @Insert
    suspend fun insert(user: User)

    @Update
    suspend fun update(user: User)

    @Query("select * from users")
    suspend fun getUser(): List<User>

    @Query("delete from users")
    suspend fun deleteAll()
}