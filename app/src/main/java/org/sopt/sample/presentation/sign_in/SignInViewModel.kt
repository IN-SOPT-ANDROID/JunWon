package org.sopt.sample.presentation.sign_in

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import org.sopt.sample.data.local.SoptSharedPreference
import org.sopt.sample.data.model.UserInfo
import javax.inject.Inject

@HiltViewModel
class SignInViewModel @Inject constructor(private val soptSharedPreference: SoptSharedPreference) :
    ViewModel() {
    val userid = MutableStateFlow("")
    val userPassword = MutableStateFlow("")
    private var _isAutoMode = MutableStateFlow(soptSharedPreference.isAutoLogin)
    val isAutoMode = _isAutoMode.asStateFlow()

    fun setLoginInfo(data: UserInfo) {
        userid.value = data.id
        userPassword.value = data.password
    }

    fun setAutoMode() {
        _isAutoMode.value = !isAutoMode.value
    }

    fun saveAutoMode() {
        soptSharedPreference.isAutoLogin = isAutoMode.value
    }

    fun checkLoginStatus(): Boolean =
        soptSharedPreference.userInfo.id == userid.value && soptSharedPreference.userInfo.password == userPassword.value
}
