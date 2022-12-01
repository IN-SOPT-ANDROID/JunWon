package org.sopt.sample.data.model.response

import kotlinx.serialization.Serializable

@Serializable
data class SignInResponse(
    val message: String,
    val result: UserResponse,
    val status: Int
)
