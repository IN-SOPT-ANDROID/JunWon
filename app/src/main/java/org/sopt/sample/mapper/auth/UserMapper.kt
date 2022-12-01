package org.sopt.sample.mapper.auth

import org.sopt.sample.data.entity.User
import org.sopt.sample.data.model.response.UserResponse
import org.sopt.sample.mapper.BaseMapper
import javax.inject.Inject

class UserMapper @Inject constructor() : BaseMapper<UserResponse, User> {
    override fun map(from: UserResponse): User =
        User(from.email, from.name)
}
