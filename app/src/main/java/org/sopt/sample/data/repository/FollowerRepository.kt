package org.sopt.sample.data.repository

import org.sopt.sample.data.entity.Follower

interface FollowerRepository {

    suspend fun getFollowers(): Result<List<Follower>>
}
