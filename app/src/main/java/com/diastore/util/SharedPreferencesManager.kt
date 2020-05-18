package com.diastore.util


import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import com.diastore.DiaStoreActivity
import com.diastore.model.User

class SharedPreferencesManager(activity: DiaStoreActivity) {
    private var sharedPreferences: SharedPreferences = activity.getPreferences(MODE_PRIVATE)

    fun saveCurrentUser(user: User) {
        with(sharedPreferences.edit()) {
            putString(ID_KEY, user.id)
            putString(FIRST_NAME_KEY, user.firstName)
            putString(LAST_NAME_KEY, user.lastName)
            putString(EMAIL_KEY, user.email)
            putString(PASSWORD_KEY, user.password)
            putInt(WEIGHT_KEY, user.weight)
            putInt(HEIGHT_KEY, user.height)
            putFloat(CTI_KEY, user.carbsToInsulinRatio.toFloat())
            putFloat(BTI_KEY, user.bloodGlucoseToInsulinRatio.toFloat())
            putString(BDAY_KEY, user.birthDate)
            commit()
        }
    }

    fun getCurrentUser(): User? {
        with(sharedPreferences) {
            val userId = getString(ID_KEY, "")
            return if (userId != "") {
                User(
                    userId!!,
                    getString(FIRST_NAME_KEY, "")!!,
                    getString(LAST_NAME_KEY, "")!!,
                    getString(EMAIL_KEY, "")!!,
                    getString(PASSWORD_KEY, "")!!,
                    getInt(WEIGHT_KEY, 0),
                    getInt(HEIGHT_KEY, 0),
                    getFloat(CTI_KEY, 0f).toDouble(),
                    getFloat(BTI_KEY, 0f).toDouble(),
                    getString(BDAY_KEY, "")!!
                )
            } else {
                null
            }
        }
    }

    fun clearUserData() {
        sharedPreferences.edit().clear().apply()
    }

    companion object {
        private const val ID_KEY = "ID_KEY"
        private const val FIRST_NAME_KEY = "FIRST_NAME_KEY"
        private const val LAST_NAME_KEY = "LAST_NAME_KEY"
        private const val EMAIL_KEY = "EMAIL_KEY"
        private const val PASSWORD_KEY = "PASSWORD_KEY"
        private const val WEIGHT_KEY = "WEIGHT_KEY"
        private const val HEIGHT_KEY = "HEIGHT_KEY"
        private const val CTI_KEY = "CTI_KEY"
        private const val BTI_KEY = "BTI_KEY"
        private const val BDAY_KEY = "BDAY_KEY"
    }
}