package com.diastore.service

import com.diastore.model.User
import com.diastore.model.dto.UpdateUserInfoDTO
import com.diastore.model.dto.UserLogInDTO
import com.diastore.model.dto.UserSignUpDTO
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import java.util.UUID

interface UserService {

    @POST("/api/users/login")
    suspend fun loginUser(@Body userLogInDto: UserLogInDTO): User

    @PUT("/api/users/{id}")
    suspend fun updateUser(@Path("id") id: UUID, @Body updateUserInfoDto: UpdateUserInfoDTO): User

    @POST("/api/users")
    suspend fun signUpUser(@Body user: UserSignUpDTO): User
}