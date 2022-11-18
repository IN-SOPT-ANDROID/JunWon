package org.sopt.sample.data.model.response

import kotlinx.serialization.Serializable

@Serializable
data class UserResponse(
    val bio: String?,
    val email: String,
    val id: Int,
    val name: String,
    val password: String,
    val profileImage: String?
)
