package com.diastore.shared

import com.diastore.feature.authentication.login.LoginViewModel
import com.diastore.feature.authentication.signup.SignUpViewModel
import com.diastore.feature.authentication.welcome.WelcomeViewModel
import com.diastore.feature.entrydetails.EntryDetailsViewModel
import com.diastore.feature.home.HomeViewModel
import com.diastore.feature.settings.SettingsViewModel
import com.diastore.service.UserService
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit

val viewModels = module {
    single { LoginViewModel(get()) }
    single { WelcomeViewModel() }
    single { SignUpViewModel(get()) }
    single { HomeViewModel() }
    single { SettingsViewModel() }
    single { EntryDetailsViewModel() }
}

val networkModule = module {
    single { provideDefaultOkHttpClient() }
    single { provideRetrofit(get()) }
    single { provideUserService(get()) }
}

fun provideDefaultOkHttpClient(): OkHttpClient {
    val interceptor = HttpLoggingInterceptor()
    interceptor.level = HttpLoggingInterceptor.Level.BODY

    val headerAuthorizationInterceptor = Interceptor { chain ->

        val request = chain.request().url().newBuilder().build()
        val newRequest = chain.request().newBuilder()
            .header("Content-Type", "application/json")
            .url(request)
            .build()
        chain.proceed(newRequest)
    }

    val logging = HttpLoggingInterceptor()
    // sets desired log level
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
    return Retrofit.Builder()
        .baseUrl("https://jsonplaceholder.typicode.com")
        .addConverterFactory(MoshiConverterFactory.create())
        .client(client)
        .addCallAdapterFactory(CoroutineCallAdapterFactory())
        .build()
}

fun provideUserService(retrofit: Retrofit): UserService = retrofit.create(UserService::class.java)