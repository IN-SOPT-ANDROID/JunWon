package org.sopt.sample.mapper.follower

import org.sopt.sample.data.entity.Follower
import org.sopt.sample.data.entity.FollowerContent
import org.sopt.sample.data.model.response.FollowerResponse
import org.sopt.sample.mapper.BaseMapper
import javax.inject.Inject

class FollowerMapper @Inject constructor() : BaseMapper<FollowerResponse, Follower> {

    override fun map(from: FollowerResponse): Follower {
        return FollowerContent(
            id = from.id,
            email = from.email,
            imageUrl = from.avatar,
            name = from.firstName
        )
    }
}
