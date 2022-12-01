package org.sopt.sample.presentation.my_page.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.sopt.sample.data.entity.Follower
import org.sopt.sample.data.entity.FollowerHeader
import org.sopt.sample.data.entity.User
import org.sopt.sample.data.repository.FollowerRepository
import org.sopt.sample.data.repository.UserRepository
import org.sopt.sample.util.UiState
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    userRepository: UserRepository,
    followerRepository: FollowerRepository
) :
    ViewModel() {
    private var _user = MutableStateFlow(userRepository.getUser())
    val user: StateFlow<User> = _user.asStateFlow()
    private var _uiState: MutableStateFlow<UiState<HomeUiStateModel>> = MutableStateFlow(UiState.Loading)
    val uiState = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            followerRepository.getFollowers()
                .onSuccess { followers ->
                    if (followers.isEmpty()) {
                        _uiState.update {
                            UiState.Empty
                            return@onSuccess
                        }
                    }
                    _uiState.update { UiState.Success(HomeUiStateModel(listOf(FollowerHeader()) + followers)) }
                }
                .onFailure { throwable ->
                    Timber.e(throwable)
                    _uiState.update {
                        UiState.Error(throwable)
                    }
                }
        }
    }

    data class HomeUiStateModel(
        val followers: List<Follower> = emptyList()
    )
}
