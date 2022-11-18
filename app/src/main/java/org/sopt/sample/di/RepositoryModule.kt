package org.sopt.sample.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import org.sopt.sample.data.repository.AuthRepository
import org.sopt.sample.data.repository.DefaultAuthRepository
import org.sopt.sample.data.repository.DefaultUserRepository
import org.sopt.sample.data.repository.UserRepository
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    @Binds
    @Singleton
    abstract fun bindsAuthRepository(defaultAuthRepository: DefaultAuthRepository): AuthRepository

    @Binds
    @Singleton
    abstract fun bindsUserRepository(defaultUserRepository: DefaultUserRepository): UserRepository
}
