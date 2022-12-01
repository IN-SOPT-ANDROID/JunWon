package org.sopt.sample.di

import android.content.Context
import android.content.SharedPreferences
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import org.sopt.sample.BuildConfig
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object SharedPrefModule {
    private const val FILE_NAME = "Murjune_Encrypted_Settings"

    @Provides
    @Singleton
    fun providesLocalPreferences(@ApplicationContext context: Context): SharedPreferences {
        return if (BuildConfig.DEBUG) context.getSharedPreferences(
            FILE_NAME,
            Context.MODE_PRIVATE
        )
        else EncryptedSharedPreferences.create(
            context,
            context.packageName,
            MasterKey.Builder(context).setKeyScheme(MasterKey.KeyScheme.AES256_GCM).build(),
            EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
            EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
        )
    }
}
