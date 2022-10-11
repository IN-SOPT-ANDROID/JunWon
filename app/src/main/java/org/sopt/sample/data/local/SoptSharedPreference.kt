package org.sopt.sample.data.local

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey
import com.google.gson.GsonBuilder
import com.google.gson.JsonParseException
import com.google.gson.reflect.TypeToken
import dagger.hilt.android.qualifiers.ApplicationContext
import org.sopt.sample.BuildConfig
import org.sopt.sample.data.entity.UserInfo
import timber.log.Timber
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SoptSharedPreference @Inject constructor(@ApplicationContext context: Context) {
    private val masterKey = MasterKey.Builder(context, MasterKey.DEFAULT_MASTER_KEY_ALIAS)
        .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
        .build()

    private val dataStore: SharedPreferences =
        if (BuildConfig.DEBUG) context.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE)
        else EncryptedSharedPreferences.create(
            context,
            FILE_NAME,
            masterKey,
            EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
            EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
        )

    var userInfo: UserInfo
        set(value) = dataStore.edit {
            val userInfo = GsonBuilder().create().toJson(value)
            putString(USER_INFO_KEY, userInfo)
            Timber.i("userInfo data save")
        }
        get() {
            val json = dataStore.getString(USER_INFO_KEY, "")
            val typeToken = object : TypeToken<UserInfo>() {}.type
            return try {
                GsonBuilder().create().fromJson<UserInfo>(json, typeToken)
            } catch (e: JsonParseException) {
                Timber.e(e.localizedMessage)
                UserInfo()
            } catch (e: NullPointerException) {
                Timber.e(e.localizedMessage)
                UserInfo()
            }
        }

    var isAutoLogin: Boolean
        set(value) = dataStore.edit { putBoolean(AUTO_LOGIN_KEY, value) }
        get() = dataStore.getBoolean(AUTO_LOGIN_KEY, false)

    companion object {
        const val FILE_NAME = "Murjune_Encrypted_Settings"
        const val AUTO_LOGIN_KEY = "오토오토로그인~"
        const val USER_INFO_KEY = "유저유저인포~"
    }
}
