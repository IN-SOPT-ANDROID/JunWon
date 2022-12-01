package org.sopt.sample.data.repository

import org.sopt.sample.data.datasource.local.UserDataSource
import org.sopt.sample.data.entity.User
import javax.inject.Inject

class DefaultUserRepository @Inject constructor(private val userDataSource: UserDataSource) :
    UserRepository {

    override fun getUser() = userDataSource.user

    override fun setUser(user: User) {
        userDataSource.user = user
    }
}
