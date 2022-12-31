package org.sopt.sample.data.model.response

import kotlinx.serialization.Serializable

@Serializable
data class MusicResponse(
    val id: Int,
    val image: String,
    val title: String,
    val singer: String
)
