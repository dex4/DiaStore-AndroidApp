package com.diastore.util


import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import com.diastore.DiaStoreActivity
import com.diastore.model.User
import java.util.UUID

class SharedPreferencesManager(activity: DiaStoreActivity) {
    private var sharedPreferences: SharedPreferences = activity.getPreferences(MODE_PRIVATE)

    fun saveCurrentUser(user: User) {
        with(sharedPreferences.edit()) {
            putString(ID_KEY, user.id.toString())
            putString(FIRST_NAME_KEY, user.firstName)
            putString(LAST_NAME_KEY, user.lastName)
            putString(EMAIL_KEY, user.email)
            putString(PASSWORD_KEY, user.password)
            putInt(WEIGHT_KEY, user.weight)
            putInt(HEIGHT_KEY, user.height)
            putInt(CTI_KEY, user.carbsToInsulinRatio)
            putInt(BTI_KEY, user.bloodGlucoseToInsulinRatio)
            putInt(BDAY_KEY, user.age)
            commit()
        }
    }

    fun getCurrentUser(): User? {
        with(sharedPreferences) {
            val userId = getString(ID_KEY, "")
            return if (userId != "") {
                User(
                    UUID.fromString(userId!!),
                    getString(FIRST_NAME_KEY, "")!!,
                    getString(LAST_NAME_KEY, "")!!,
                    getString(EMAIL_KEY, "")!!,
                    getString(PASSWORD_KEY, "")!!,
                    getInt(WEIGHT_KEY, 0),
                    getInt(HEIGHT_KEY, 0),
                    getInt(CTI_KEY, 0),
                    getInt(BTI_KEY, 0),
                    getInt(BDAY_KEY, 0)
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