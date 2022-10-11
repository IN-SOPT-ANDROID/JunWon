package org.sopt.sample.data.entity

data class FollowerInfo(
    override val id: Int = 0,
    val imageUrl: String? = null,
    val name: String = "",
    val description: String? = "no description"
) : Follower
