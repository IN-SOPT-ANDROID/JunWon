package org.sopt.sample.di

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import okhttp3.Interceptor
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.sopt.sample.BuildConfig
import org.sopt.sample.di.type.RetrofitType
import retrofit2.Retrofit
import java.util.concurrent.TimeUnit
import javax.inject.Qualifier
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RetrofitModule {

    private const val SOPT_BASE_URL: String = BuildConfig.SOPT_BASE_URL
    private const val REQRES_BASE_URL: String = BuildConfig.REQRES_BASE_URL
    private const val MUSIC_URL: String = BuildConfig.MUSIC_URL

    @Provides
    @Singleton
    fun provideJson(): Json = Json {
        isLenient = true
        ignoreUnknownKeys = true
        coerceInputValues = true
        encodeDefaults = true
    }

    @Provides
    @Singleton
    fun providesInterceptor(): Interceptor =
        Interceptor { chain ->
            chain.proceed(
                chain.request()
                    .newBuilder()
                    .addHeader("Content-Type", "multipart/form-data")
                    .build()
            )
        }

    @Provides
    @Singleton
    fun providesOkHttpClient(interceptor: Interceptor): OkHttpClient =
        OkHttpClient
            .Builder()
            .connectTimeout(10, TimeUnit.SECONDS)
            .writeTimeout(10, TimeUnit.SECONDS)
            .readTimeout(10, TimeUnit.SECONDS)
            .apply {
                addInterceptor(interceptor)
                if (BuildConfig.DEBUG) {
                    addInterceptor(
                        HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BODY }
                    )
                }
            }
            .build()

    @OptIn(ExperimentalSerializationApi::class)
    @Provides
    @Retrofit2(RetrofitType.SOPT)
    @Singleton
    fun providesRetrofit(okHttpClient: OkHttpClient, jsonCustomFormat: Json): Retrofit =
        Retrofit.Builder()
            .baseUrl(SOPT_BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(
                jsonCustomFormat.asConverterFactory("application/json".toMediaType())
            )
            .build()

    @OptIn(ExperimentalSerializationApi::class)
    @Provides
    @Singleton
    @Retrofit2(RetrofitType.REQ_RES)
    fun providesReqResRetrofit(okHttpClient: OkHttpClient, jsonCustomFormat: Json): Retrofit =
        Retrofit.Builder()
            .baseUrl(REQRES_BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(
                jsonCustomFormat.asConverterFactory("application/json".toMediaType())
            )
            .build()

    @OptIn(ExperimentalSerializationApi::class)
    @Provides
    @Singleton
    @Retrofit2(RetrofitType.MUSIC)
    fun providesMusicRetrofit(okHttpClient: OkHttpClient, jsonCustomFormat: Json): Retrofit =
        Retrofit.Builder()
            .baseUrl(MUSIC_URL)
            .client(okHttpClient)
            .addConverterFactory(
                jsonCustomFormat.asConverterFactory("application/json".toMediaType())
            )
            .build()

    @Qualifier
    annotation class Retrofit2(val type: RetrofitType)
}
