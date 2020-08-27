package com.diastore

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class DiaStoreActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.AppTheme)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_diastore)

        (application as DiaStoreApplication).startKoinApplication()
    }
}
