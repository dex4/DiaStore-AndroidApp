package com.diastore.shared

import androidx.room.Room
import com.diastore.database.DiaStoreDataBase
import com.diastore.feature.authentication.login.LoginViewModel
import com.diastore.feature.authentication.signup.SignUpViewModel
import com.diastore.feature.authentication.welcome.WelcomeViewModel
import com.diastore.feature.entrydetails.EntryDetailsViewModel
import com.diastore.feature.home.HomeViewModel
import com.diastore.feature.settings.SettingsViewModel
import com.diastore.repo.EntriesRepository
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module

val viewModels = module {
    single { LoginViewModel(get()) }
    single { WelcomeViewModel() }
    single { SignUpViewModel(get()) }
    single { HomeViewModel(get()) }
    single { SettingsViewModel() }
    single { EntryDetailsViewModel() }
}

val repositories = module {
    factory { EntriesRepository(get()) }
    single {
        Room.databaseBuilder(
            androidApplication(),
            DiaStoreDataBase::class.java,
            "diastore_database"
        ).fallbackToDestructiveMigration().build()
    }
    single { get<DiaStoreDataBase>().entriesDao() }
}