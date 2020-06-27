package com.diastore

import android.app.Application
import com.diastore.shared.networkModule
import com.diastore.shared.repositories
import com.diastore.shared.sharedPreferencesManager
import com.diastore.shared.viewModels
import com.jakewharton.threetenabp.AndroidThreeTen
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class DiaStoreApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        AndroidThreeTen.init(this)
        startKoin {
            androidContext(this@DiaStoreApplication)
            modules(listOf(networkModule, repositories, viewModels, sharedPreferencesManager))
        }
    }
}