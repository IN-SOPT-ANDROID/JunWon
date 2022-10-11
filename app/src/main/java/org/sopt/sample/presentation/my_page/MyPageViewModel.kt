package org.sopt.sample.presentation.my_page

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import org.sopt.sample.data.local.SoptSharedPreference
import javax.inject.Inject

@HiltViewModel
class MyPageViewModel @Inject constructor(private val soptSharedPreference: SoptSharedPreference) :
    ViewModel() {

    private var _userMbti = MutableStateFlow(soptSharedPreference.userInfo.mbti)
    val userMbti: StateFlow<String> = _userMbti.asStateFlow()
}
