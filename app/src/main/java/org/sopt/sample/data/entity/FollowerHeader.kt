package org.sopt.sample.data.entity

data class FollowerHeader(
    override val id: Int = -1,
    val title: String = "Murjune's Follower들~"
) : Follower()
