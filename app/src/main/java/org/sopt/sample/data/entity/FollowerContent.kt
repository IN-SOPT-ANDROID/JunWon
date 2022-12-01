package org.sopt.sample.data.entity

data class FollowerContent(
    override val id: Int = 0,
    val email: String,
    val imageUrl: String? = null,
    val name: String = ""
) : Follower()
