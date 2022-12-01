package org.sopt.sample.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import org.sopt.sample.data.repository.AuthRepository
import org.sopt.sample.data.repository.DefaultAuthRepository
import org.sopt.sample.data.repository.DefaultFollowerRepository
import org.sopt.sample.data.repository.DefaultUserRepository
import org.sopt.sample.data.repository.FollowerRepository
import org.sopt.sample.data.repository.UserRepository

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    @Binds
    abstract fun bindsAuthRepository(defaultAuthRepository: DefaultAuthRepository): AuthRepository

    @Binds
    abstract fun bindsUserRepository(defaultUserRepository: DefaultUserRepository): UserRepository

    @Binds
    abstract fun bindFollowerRepository(defaultFollowerRepository: DefaultFollowerRepository): FollowerRepository
}
