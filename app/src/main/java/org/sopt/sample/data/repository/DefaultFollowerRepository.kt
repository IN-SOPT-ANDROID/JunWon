package org.sopt.sample.data.repository

import org.sopt.sample.data.datasource.local.FollowerDataSource
import org.sopt.sample.data.entity.Follower
import org.sopt.sample.mapper.follower.FollowerMapper
import javax.inject.Inject

class DefaultFollowerRepository @Inject constructor(
    private val followerDataSource: FollowerDataSource,
    private val followerMapper: FollowerMapper
) : FollowerRepository {
    override suspend fun getFollowers(): Result<List<Follower>> {
        return runCatching {
            followerDataSource.getFollowers().data.map {
                followerMapper.map(it)
            }
        }
    }
}
