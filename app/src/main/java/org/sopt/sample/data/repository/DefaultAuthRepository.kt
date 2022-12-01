package org.sopt.sample.data.repository

import org.sopt.sample.data.datasource.local.AutoSignInDataSource
import org.sopt.sample.data.datasource.remote.AuthDataSource
import org.sopt.sample.data.entity.User
import org.sopt.sample.data.model.request.SignInRequest
import org.sopt.sample.data.model.request.SignUpRequest
import org.sopt.sample.mapper.auth.UserMapper
import javax.inject.Inject

class DefaultAuthRepository @Inject constructor(
    private val authDataSource: AuthDataSource,
    private val autoSignInDataSource: AutoSignInDataSource,
    private val userMapper: UserMapper
) : AuthRepository {

    override fun clearPreference() = autoSignInDataSource.clearPref()

    override fun getAutoMode() = autoSignInDataSource.isAutoLogin
    override fun setAutoMode(isAutoMode: Boolean) {
        autoSignInDataSource.isAutoLogin = isAutoMode
    }

    override suspend fun postSignIn(email: String, password: String): Result<User> =
        runCatching {
            val response = authDataSource.postSignIn(SignInRequest(email, password))
            userMapper.map(response.result)
        }

    override suspend fun postSignUp(
        email: String,
        password: String,
        name: String
    ): Result<User> = runCatching {
        val response = authDataSource.postSignUp(SignUpRequest(email, password, name))
        userMapper.map(response.newUser)
    }
}
