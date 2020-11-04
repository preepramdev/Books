package com.pram.book.data.local.sharedpreferences

import android.content.SharedPreferences
import com.pram.book.MainApplication
import javax.inject.Inject

class MainSharedPreferences {

    @Inject
    lateinit var preferences: SharedPreferences

    private val IS_FIRST_LAUNCH = "is_first_launch"

    init {
        MainApplication.appComponent.inject(this)
    }

    var isFirstLaunch: Boolean
        get() = preferences.getBoolean(IS_FIRST_LAUNCH, true)
        set(value) = preferences.edit().putBoolean(IS_FIRST_LAUNCH, value).apply()

    fun clearPreferences() {
        preferences.edit().apply {
            remove(IS_FIRST_LAUNCH).apply()
        }
    }
}