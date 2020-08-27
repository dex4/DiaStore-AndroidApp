package com.diastore.shared

import com.diastore.model.dto.OffsetDateTimeAdapter
import com.diastore.model.dto.UUIDAdapter
import com.diastore.service.EntriesService
import com.diastore.service.UserService
import com.squareup.moshi.Moshi
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit

val networkModule = module {
    single { provideDefaultOkHttpClient() }
    single { provideRetrofit(get()) }
    single { provideUserService(get()) }
    single { provideEntriesService(get()) }
}

fun provideDefaultOkHttpClient(): OkHttpClient {
    val interceptor = HttpLoggingInterceptor()
    interceptor.level = HttpLoggingInterceptor.Level.BODY

    val headerAuthorizationInterceptor = Interceptor { chain ->

        val request = chain.request().url.newBuilder().build()
        val newRequest = chain.request().newBuilder()
            .header("Content-Type", "application/json")
            .url(request)
            .build()
        chain.proceed(newRequest)
    }

    val logging = HttpLoggingInterceptor()

    logging.level = HttpLoggingInterceptor.Level.BODY

    return OkHttpClient.Builder()
        .addNetworkInterceptor(interceptor)
        .addInterceptor(logging)
        .addInterceptor(headerAuthorizationInterceptor)
        .connectTimeout(1, TimeUnit.MINUTES)
        .readTimeout(30, TimeUnit.SECONDS)
        .writeTimeout(15, TimeUnit.SECONDS)
        .build()
}

fun provideRetrofit(client: OkHttpClient): Retrofit {
    val moshi = Moshi.Builder()
        .add(UUIDAdapter())
        .add(OffsetDateTimeAdapter())
        .build()
    return Retrofit.Builder()
        .baseUrl("https://diastore-api.conveyor.cloud")
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        .client(client)
        .build()
}

fun provideUserService(retrofit: Retrofit): UserService = retrofit.create(UserService::class.java)
fun provideEntriesService(retrofit: Retrofit): EntriesService = retrofit.create(EntriesService::class.java)