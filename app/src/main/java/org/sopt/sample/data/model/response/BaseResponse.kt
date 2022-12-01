package org.sopt.sample.data.model.response

import kotlinx.serialization.Serializable

@Serializable
data class BaseResponse<T>(
    val message: String,
    val data: T,
    val status: Int
)
