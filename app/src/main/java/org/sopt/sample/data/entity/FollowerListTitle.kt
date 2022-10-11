package org.sopt.sample.data.entity

// 이거 class 이름을 뭐로 해야할까요.. ㅜㅜ 추천점
data class FollowerListTitle(
    override val id: Int = -1,
    val title: String = "Murjune's Follower들~"
) : Follower
