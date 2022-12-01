package org.sopt.sample.data.datasource.local

import org.sopt.sample.data.model.response.FollowersResponse
import org.sopt.sample.data.service.HomeService
import javax.inject.Inject

class FollowerDataSource @Inject constructor(
    private val homeService: HomeService
) {
    suspend fun getFollowers(): FollowersResponse {
        return homeService.getFollowers()
    }
}
