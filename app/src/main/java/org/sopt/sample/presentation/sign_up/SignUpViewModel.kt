package org.sopt.sample.presentation.sign_up

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import org.sopt.sample.data.local.SoptSharedPreference
import org.sopt.sample.data.model.UserInfo
import org.sopt.sample.presentation.type.MbtiType
import org.sopt.sample.util.extension.isLetterOrDigit
import org.sopt.sample.util.extension.isRange
import org.sopt.sample.util.extension.safeValueOf
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(private val soptSharedPreference: SoptSharedPreference) :
    ViewModel() {
    val userId = MutableStateFlow("")
    val userPassWord = MutableStateFlow("")
    val userMbti = MutableStateFlow("")
    val isValidSignUpFormat: StateFlow<Boolean> = userId.combine(userPassWord) { id, pw ->
        id.length.isRange(6, 10) && pw.length.isRange(8, 12) && id.isLetterOrDigit()
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000L), false)

    fun getUserInfo() =
        UserInfo().copy(
            id = userId.value,
            password = userPassWord.value,
            mbti = safeValueOf<MbtiType>(userMbti.value.uppercase())?.name ?: ""
        )

    fun saveUserInfo() {
        soptSharedPreference.userInfo = getUserInfo()
    }
}
