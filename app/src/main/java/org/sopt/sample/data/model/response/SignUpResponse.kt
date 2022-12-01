package org.sopt.sample.data.model.response

import kotlinx.serialization.Serializable

@Serializable
data class SignUpResponse(
    val message: String,
    val newUser: UserResponse,
    val status: Int
)
