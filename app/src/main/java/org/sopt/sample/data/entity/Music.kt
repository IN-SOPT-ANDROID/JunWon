package org.sopt.sample.data.entity

import kotlinx.serialization.Serializable

@Serializable
data class Music(
    val id: Int = -1,
    val image: String = "",
    val title: String = "",
    val singer: String = ""
)
