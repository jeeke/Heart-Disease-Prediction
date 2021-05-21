package me.gyanesh.hdp.data

import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatDelegate
import me.gyanesh.hdp.HDPApp

object PreferenceProvider {

    private const val FIRST_TIME = "first_time"
    private const val NIGHT_MODE = "night_mode"
    private const val HAS_RATED = "has_rated"

    private val nightMode: Int
        get() = preference.getInt(
            NIGHT_MODE,
            AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM
        )

    fun setCurrentTheme() {
        AppCompatDelegate.setDefaultNightMode(nightMode)
    }

    fun changeAppTheme(theme: String) {
        val nightMode = when (theme) {
            "light" -> AppCompatDelegate.MODE_NIGHT_NO
            "dark" -> AppCompatDelegate.MODE_NIGHT_YES
            else -> AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM
        }
        preference.edit().putInt(NIGHT_MODE, nightMode).apply()
        AppCompatDelegate.setDefaultNightMode(nightMode)
    }

    fun onOnboardingComplete() {
        preference.edit().putBoolean(
            FIRST_TIME,
            false
        ).apply()
    }

    fun isFirstTime(): Boolean = preference.getBoolean(FIRST_TIME, true)

    private val preference: SharedPreferences
        get() = HDPApp.getInstance().getSharedPreferences(
            "hdp-preferences",
            Context.MODE_PRIVATE
        )

}