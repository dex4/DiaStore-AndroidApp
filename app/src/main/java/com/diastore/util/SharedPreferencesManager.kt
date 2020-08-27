package com.diastore.util


import android.content.Context
import android.content.SharedPreferences
import androidx.preference.PreferenceManager

class SharedPreferencesManager(context: Context) {
    private val sharedPreferences: SharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)
    private var isUserLoggedIn: Boolean
        get() = sharedPreferences.getBoolean(USER_LOGGED_IN_KEY, false)
        set(value) {
            sharedPreferences.edit().putBoolean(USER_LOGGED_IN_KEY, value).apply()
        }

    fun saveUser(userId: String, firstName: String, lastName: String) {
        with(sharedPreferences.edit()) {
            isUserLoggedIn = true
            putString(ID_KEY, userId)
            putString(FIRST_NAME_KEY, firstName)
            putString(LAST_NAME_KEY, lastName)
            apply()
        }
    }

    fun getUserFirstName() = sharedPreferences.getString(FIRST_NAME_KEY, "")

    fun getUserLastName() = sharedPreferences.getString(LAST_NAME_KEY, "")

    fun getUserId() = sharedPreferences.getString(ID_KEY, "")

    fun getIsUserLoggedIn() = sharedPreferences.getBoolean(USER_LOGGED_IN_KEY, false)

    fun clearUserData() {
        sharedPreferences.edit().clear().apply()
    }

    companion object {
        private const val USER_LOGGED_IN_KEY = "USER_LOGGED_IN_KEY"
        private const val ID_KEY = "ID_KEY"
        private const val FIRST_NAME_KEY = "FIRST_NAME_KEY"
        private const val LAST_NAME_KEY = "LAST_NAME_KEY"
    }
}