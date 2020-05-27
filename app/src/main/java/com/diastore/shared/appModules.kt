package com.diastore.shared

import androidx.room.Room
import com.diastore.database.DiaStoreDataBase
import com.diastore.feature.authentication.login.LoginViewModel
import com.diastore.feature.authentication.signup.SignUpViewModel
import com.diastore.feature.entrydetails.EntryDetailsViewModel
import com.diastore.feature.home.HomeViewModel
import com.diastore.feature.settings.ProfileViewModel
import com.diastore.repo.EntriesRepository
import com.diastore.repo.UserRepository
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module

val viewModels = module {
    single { LoginViewModel(get()) }
    single { SignUpViewModel(get()) }
    single { HomeViewModel(get()) }
    single { ProfileViewModel(get(), get()) }
    single { EntryDetailsViewModel() }
}

val repositories = module {
    factory { EntriesRepository(get()) }
    factory { UserRepository(get(), get()) }
    single {
        Room.databaseBuilder(
            androidApplication(),
            DiaStoreDataBase::class.java,
            "diastore_database"
        ).fallbackToDestructiveMigration().build()
    }
    single { get<DiaStoreDataBase>().entriesDao() }
    single { get<DiaStoreDataBase>().usersDao() }
}