package com.diastore

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class DiaStoreActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.AppTheme)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_diastore)
    }
}
