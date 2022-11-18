package org.sopt.sample.presentation.my_page.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.sopt.sample.data.entity.Follower
import org.sopt.sample.data.entity.FollowerHeader
import org.sopt.sample.data.entity.User
import org.sopt.sample.data.repository.FollowerRepository
import org.sopt.sample.data.repository.UserRepository
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    userRepository: UserRepository,
    followerRepository: FollowerRepository
) :
    ViewModel() {
    private var _followerList: MutableStateFlow<List<Follower>> =
        MutableStateFlow(listOf(FollowerHeader()))
    val followerList: StateFlow<List<Follower>> = _followerList.asStateFlow()
    private var _user = MutableStateFlow(userRepository.getUser())
    val user: StateFlow<User> = _user.asStateFlow()

    init {
        viewModelScope.launch {
            followerRepository.getFollowers()
                .onSuccess { followers ->
                    _followerList.value = listOf(FollowerHeader()) + followers
                }
                .onFailure {
                    Timber.e(it)
                }
        }
    }
}
