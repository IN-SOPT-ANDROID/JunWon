package org.sopt.sample.data.repository

import okhttp3.MultipartBody
import org.sopt.sample.data.entity.Music

interface MusicRepository {
    suspend fun getMusics(): Result<List<Music>>
    suspend fun postMusic(
        title: String,
        singer: String,
        image: MultipartBody.Part
    ): Result<Music>
}
