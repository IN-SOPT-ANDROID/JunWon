package org.sopt.sample.presentation.sign_in

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import org.sopt.sample.data.repository.DefaultAuthRepository
import org.sopt.sample.data.repository.UserRepository
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class SignInViewModel @Inject constructor(
    private val defaultAuthRepository: DefaultAuthRepository,
    private val userRepository: UserRepository
) :
    ViewModel() {
    private var _isSignInSuccess = MutableSharedFlow<Boolean>()
    val isSignInSuccess = _isSignInSuccess.asSharedFlow()
    val userid = MutableStateFlow("")
    val userPassword = MutableStateFlow("")
    private var _isAutoMode = MutableStateFlow(defaultAuthRepository.getAutoMode())
    val isAutoMode = _isAutoMode.asStateFlow()
    val isValidSignInFormat: StateFlow<Boolean> =
        combine(userPassword, userid) { pw, id ->
            id.length in (6..10) && pw.length in (8..12)
        }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000L), false)

    fun setSignInContent() {
        val user = userRepository.getUser()
        Timber.e(user.toString())
        userid.value = user.email
        userPassword.value = user.password
    }

    fun setAutoMode() {
        _isAutoMode.value = !isAutoMode.value
    }

    fun postSignIn() {
        viewModelScope.launch {
            defaultAuthRepository.postSignIn(email = userid.value, password = userPassword.value)
                .onSuccess { user ->
                    defaultAuthRepository.setAutoMode(isAutoMode.value)
                    userRepository.setUser(user)
                    _isSignInSuccess.emit(true)
                }
                .onFailure {
                    _isSignInSuccess.emit(false)
                    Timber.e(it)
                }
        }
    }
}
