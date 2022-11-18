package org.sopt.sample.data.model.response

import kotlinx.serialization.Serializable

@Serializable
data class NoDataResponse(
    val message: String,
    val status: Int
)
