package org.sopt.sample.data.service

import org.sopt.sample.data.model.response.FollowersResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface HomeService {
    @GET("users")
    suspend fun getFollowers(
        @Query("page") page: Int = 1,
        @Query("per_page") perPage: Int = 20
    ): FollowersResponse
}
