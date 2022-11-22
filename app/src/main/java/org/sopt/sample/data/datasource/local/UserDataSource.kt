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

class UserDataSource @Inject constructor(
    private val pref: SharedPreferences,
    private val jsonCustomFormat: Json
) {
    var user: User
        set(value) = pref.edit {
            val user = jsonCustomFormat.encodeToString(value)
            putString(AutoSignInDataSource.USER_INFO_KEY, user)
        }
        get() {
            val jsonFromDataSource = pref.getString(AutoSignInDataSource.USER_INFO_KEY, "")
            return try {
                jsonCustomFormat.decodeFromString<User>(jsonFromDataSource!!)
            } catch (e: SerializationException) {
                Timber.e(e.localizedMessage)
                User()
            } catch (e: IllegalArgumentException) {
                Timber.e(e.localizedMessage)
                User()
            }
        }
}
