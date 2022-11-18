package org.sopt.sample.data.repository

import org.sopt.sample.data.entity.User

interface UserRepository {
    fun getUser(): User

    fun setUser(user: User)
}
