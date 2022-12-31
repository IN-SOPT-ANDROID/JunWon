package org.sopt.sample.data.datasource.remote

import kotlinx.serialization.json.buildJsonObject
import kotlinx.serialization.json.put
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.toRequestBody
import org.sopt.sample.data.model.response.BaseResponse
import org.sopt.sample.data.model.response.MusicResponse
import org.sopt.sample.data.service.MusicService
import javax.inject.Inject

class MusicDataSource @Inject constructor(
    private val musicService: MusicService
) {
    suspend fun getMusics() = musicService.getMusics()
    suspend fun postMusic(
        title: String,
        singer: String,
        image: MultipartBody.Part
    ): BaseResponse<MusicResponse> {
        val mediaType = "application/json; charset=utf-8".toMediaType()
        val requestBody = buildJsonObject {
            put("singer", singer)
            put("title", title)
        }.toString().toRequestBody(mediaType)
        return musicService.postMusic(
            request = requestBody,
            image = image
        )
    }
}
