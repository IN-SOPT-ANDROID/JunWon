package org.sopt.sample.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import org.sopt.sample.data.service.AuthService
import org.sopt.sample.data.service.HomeService
import org.sopt.sample.data.service.MusicService
import org.sopt.sample.di.RetrofitModule.Retrofit2
import org.sopt.sample.di.type.RetrofitType
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ServiceModule {
    private inline fun <reified T> Retrofit.create(): T = this.create(T::class.java)

    @Provides
    @Singleton
    fun providesAuthService(@Retrofit2(RetrofitType.SOPT) retrofit: Retrofit): AuthService =
        retrofit.create()

    @Provides
    @Singleton
    fun providesHomeService(@Retrofit2(RetrofitType.REQ_RES) retrofit: Retrofit): HomeService =
        retrofit.create()

    @Provides
    @Singleton
    fun providesGalleyService(@Retrofit2(RetrofitType.MUSIC) retrofit: Retrofit): MusicService =
        retrofit.create()
}
