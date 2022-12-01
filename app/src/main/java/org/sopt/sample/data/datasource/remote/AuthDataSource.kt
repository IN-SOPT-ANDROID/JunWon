package org.sopt.sample.data.datasource.remote

import org.sopt.sample.data.model.request.SignInRequest
import org.sopt.sample.data.model.request.SignUpRequest
import org.sopt.sample.data.model.response.SignInResponse
import org.sopt.sample.data.model.response.SignUpResponse
import org.sopt.sample.data.service.AuthService
import javax.inject.Inject

class AuthDataSource @Inject constructor(private val authService: AuthService) {

    suspend fun postSignIn(signInRequest: SignInRequest): SignInResponse =
        authService.postSignIn(signInRequest)

    suspend fun postSignUp(signUpRequest: SignUpRequest): SignUpResponse =
        authService.postSignUp(signUpRequest)
}
