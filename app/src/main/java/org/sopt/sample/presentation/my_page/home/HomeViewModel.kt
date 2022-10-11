package org.sopt.sample.presentation.my_page.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import org.sopt.sample.data.entity.Follower
import org.sopt.sample.data.entity.FollowerInfo
import org.sopt.sample.data.entity.FollowerListTitle
import org.sopt.sample.data.local.SoptSharedPreference
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(soptSharedPreference: SoptSharedPreference) :
    ViewModel() {
    private var _uiState = MutableStateFlow(HomeUiState())
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()
    private var _userMbti = MutableStateFlow(soptSharedPreference.userInfo.mbti)
    val userMbti: StateFlow<String> = _userMbti.asStateFlow()

    init {
        viewModelScope.launch {
            flow {
                emit(
                    listOf<Follower>(
                        FollowerInfo(
                            id = 1,
                            imageUrl = "",
                            name = "이준원",
                            description = ""
                        ),
                        FollowerInfo(
                            id = 2,
                            imageUrl = "https://user-images.githubusercontent.com/87055456/195072968-6a38efc5-119b-4675-bfdb-45ce5ecc4170.png",
                            name = "이태희",
                            description = "오늘 예비군 갔다왔어요"
                        ),
                        FollowerInfo(
                            id = 3,
                            imageUrl = null,
                            name = "김준서",
                            description = "영원히 자게해주세요"
                        ),
                        FollowerInfo(
                            id = 4,
                            imageUrl = "https://user-images.githubusercontent.com/87055456/195072968-6a38efc5-119b-4675-bfdb-45ce5ecc4170.png",
                            name = "고아라",
                            description = "목표는 안드접수"
                        ),
                        FollowerInfo(
                            id = 5,
                            imageUrl = "https://user-images.githubusercontent.com/87055456/195072968-6a38efc5-119b-4675-bfdb-45ce5ecc4170.png",
                            name = "김지은",
                            description = "뿡"
                        )

                    )
                )
            }
                .collect { followerList ->
                    _uiState.value = HomeUiState().copy(
                        followerList = listOf(FollowerListTitle()) + followerList
                    )
                }
        }
    }

    data class HomeUiState(
        val followerList: List<Follower> = listOf(FollowerListTitle())
    )
}
