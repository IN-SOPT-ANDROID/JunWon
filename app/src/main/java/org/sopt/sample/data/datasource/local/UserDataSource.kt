package org.sopt.sample.data.datasource.local

import android.content.SharedPreferences
import androidx.core.content.edit
import kotlinx.serialization.SerializationException
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import org.sopt.sample.data.entity.User
import timber.log.Timber
import javax.inject.Inject

class UserDataSource @Inject constructor(private val pref: SharedPreferences) {
    var user: User
        set(value) = pref.edit {
            val user = Json.encodeToString(value)
            putString(AutoSignInDataSource.USER_INFO_KEY, user)
        }
        get() {
            val json = pref.getString(AutoSignInDataSource.USER_INFO_KEY, "")
            return try {
                Json.decodeFromString<User>(json!!)
            } catch (e: SerializationException) {
                Timber.e(e.localizedMessage)
                User()
            } catch (e: IllegalArgumentException) {
                Timber.e(e.localizedMessage)
                User()
            }
        }
}
