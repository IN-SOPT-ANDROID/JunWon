package org.sopt.sample.data.model.response

import kotlinx.serialization.Serializable

@Serializable
data class BaseResponse<T>(
    val data: T,
    val statusCode: Int,
    val success: Boolean,
    val message: String
)
