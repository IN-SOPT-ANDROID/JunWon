package org.sopt.sample.presentation.sign_up

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import org.sopt.sample.data.repository.DefaultAuthRepository
import org.sopt.sample.data.repository.UserRepository
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val authRepository: DefaultAuthRepository,
    private val userRepository: UserRepository
) :
    ViewModel() {
    val userId = MutableStateFlow("")
    val userPassWord = MutableStateFlow("")
    val userName = MutableStateFlow("")
    val isValidSignUpFormat: StateFlow<Boolean> =
        combine(userPassWord, userId, userName) { pw, id, name ->
            id.length in (6..10) && pw.length in (8..12) &&
                isLetterOrDigit(id) && name.length in (2..8)
        }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000L), false)

    fun postSignUp() =
        viewModelScope.launch {
            authRepository.postSignUp(
                email = userId.value,
                password = userPassWord.value,
                name = userName.value
            ).onSuccess { user ->
                Timber.e(user.toString())
                userRepository.setUser(
                    user.copy(password = userPassWord.value)
                )
            }.onFailure {
                Timber.e(it)
            }
        }

    private fun isLetterOrDigit(id: String) = id.all { char -> char.isLetterOrDigit() }
}
