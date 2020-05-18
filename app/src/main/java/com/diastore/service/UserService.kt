package com.diastore.service

import com.diastore.model.User
import kotlinx.coroutines.Deferred
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PUT
import retrofit2.http.Path

interface UserService {
    @PUT("/user/")
    fun registerOrUpdateUser(@Body user: User): Deferred<String>

    @GET("/user/email/{email}/password/{password}")
    fun login(@Path("email") email: String, @Path("password") password: String): Deferred<User>
}