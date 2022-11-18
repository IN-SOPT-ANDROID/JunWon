package org.sopt.sample.data.repository

import org.sopt.sample.data.entity.User

interface AuthRepository {

    fun clearPreference()
    fun getAutoMode(): Boolean
    fun setAutoMode(isAutoMode: Boolean)
    suspend fun postSignIn(
        email: String,
        password: String
    ): Result<User>

    suspend fun postSignUp(
        email: String,
        password: String,
        name: String
    ): Result<User>
}
