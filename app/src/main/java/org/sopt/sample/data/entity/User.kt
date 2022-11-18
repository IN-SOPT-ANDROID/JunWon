package org.sopt.sample.data.entity

import kotlinx.serialization.Serializable

@Serializable
data class User(
    val email: String = "",
    val name: String = "",
    val password: String = "",
    val profileImage: String? = "",
    val bio: String? = "",
    val id: String = ""
)
