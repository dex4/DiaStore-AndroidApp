package com.diastore.service

import com.diastore.model.Entry
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import java.util.UUID

interface EntriesService {

    @GET("/api/users/{id}")
    suspend fun getUserEntries(@Path("id") userId: UUID): List<Entry>

    @POST("/api/entries/{id}")
    suspend fun addEntry(@Path("id") userId: UUID, @Body entry: Entry): Entry

    @PUT("/api/entries/{id}")
    suspend fun updateEntry(@Path("id") id: UUID, @Body entry: Entry): Entry

    @DELETE("/api/entries/{id}")
    suspend fun deleteEntry(@Path("id") id: UUID): Entry
}