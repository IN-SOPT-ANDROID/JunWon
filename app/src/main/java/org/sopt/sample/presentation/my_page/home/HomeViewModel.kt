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
import org.sopt.sample.data.entity.FollowerContent
import org.sopt.sample.data.entity.FollowerHeader
import org.sopt.sample.data.entity.User
import org.sopt.sample.data.repository.UserRepository
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(userRepository: UserRepository) :
    ViewModel() {
    private var _followerList: MutableStateFlow<List<Follower>> =
        MutableStateFlow(listOf(FollowerHeader()))
    val followerList: StateFlow<List<Follower>> = _followerList.asStateFlow()
    private var _user = MutableStateFlow(userRepository.getUser())
    val user: StateFlow<User> = _user.asStateFlow()

    init {
        viewModelScope.launch {
            flow {
                emit(
                    dummyList

                )
            }.collect { followerList ->
                _followerList.value =
                    listOf(FollowerHeader()) + followerList
            }
        }
    }

    companion object {
        val dummyList = listOf<Follower>(
            FollowerContent(
                id = 1,
                imageUrl = "",
                name = "이준원",
                description = ""
            ),
            FollowerContent(
                id = 2,
                imageUrl = "https://user-images.githubusercontent.com/87055456/195072968-6a38efc5-119b-4675-bfdb-45ce5ecc4170.png",
                name = "이태희",
                description = "오늘 예비군 갔다왔어요"
            ),
            FollowerContent(
                id = 3,
                imageUrl = null,
                name = "김준서",
                description = "영원히 자게해주세요"
            ),
            FollowerContent(
                id = 4,
                imageUrl = "https://user-images.githubusercontent.com/87055456/195072968-6a38efc5-119b-4675-bfdb-45ce5ecc4170.png",
                name = "고아라",
                description = "목표는 안드접수"
            ),
            FollowerContent(
                id = 5,
                imageUrl = "https://user-images.githubusercontent.com/87055456/195072968-6a38efc5-119b-4675-bfdb-45ce5ecc4170.png",
                name = "김지은",
                description = "뿡"
            ),
            FollowerContent(
                id = 6,
                imageUrl = null,
                name = "김준서",
                description = "영원히 자게해주세요"
            ),
            FollowerContent(
                id = 7,
                imageUrl = "https://user-images.githubusercontent.com/87055456/195072968-6a38efc5-119b-4675-bfdb-45ce5ecc4170.png",
                name = "고아라",
                description = "목표는 안드접수"
            ),
            FollowerContent(
                id = 8,
                imageUrl = "https://user-images.githubusercontent.com/87055456/195072968-6a38efc5-119b-4675-bfdb-45ce5ecc4170.png",
                name = "김지은",
                description = "뿡"
            )

        )
    }
}
