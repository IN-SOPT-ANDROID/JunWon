package org.sopt.sample.data.datasource.local

import android.content.SharedPreferences
import androidx.core.content.edit
import javax.inject.Inject

class AutoSignInDataSource @Inject constructor(private val pref: SharedPreferences) {

    var isAutoLogin: Boolean
        set(value) = pref.edit { putBoolean(AUTO_LOGIN_KEY, value) }
        get() = pref.getBoolean(AUTO_LOGIN_KEY, false)

    fun clearPref() = pref.edit {
        clear()
    }

    companion object {
        const val AUTO_LOGIN_KEY = "오토오토로그인~"
        const val USER_INFO_KEY = "유저유저인포~"
    }
}
