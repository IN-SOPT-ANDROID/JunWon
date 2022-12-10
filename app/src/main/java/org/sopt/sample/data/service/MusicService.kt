package org.sopt.sample.data.service

import okhttp3.MultipartBody
import okhttp3.RequestBody
import org.sopt.sample.data.model.response.BaseResponse
import org.sopt.sample.data.model.response.MusicResponse
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface MusicService {
    @GET("/music/list")
    suspend fun getMusics(): BaseResponse<List<MusicResponse>>

    @Multipart
    @POST("/music")
    suspend fun postMusic(
        @Part("request") request: RequestBody,
        @Part image: MultipartBody.Part
    ): BaseResponse<MusicResponse>
}
