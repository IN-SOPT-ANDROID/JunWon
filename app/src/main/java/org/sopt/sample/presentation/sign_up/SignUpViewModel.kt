package org.sopt.sample.presentation.sign_up

import android.util.Patterns
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
import java.util.regex.Pattern
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
            isValidIdFormatOf(id) &&
                isValidPwFormatOf(pw) &&
                name.length in (2..8)
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

    fun isValidIdFormatOf(id: String) =
        id.isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(id).matches() && id.length in (6..10)

    fun isValidPwFormatOf(pw: String) =
        pw.isEmpty() || !Pattern.matches(PW_PATTERN, pw) && pw.length in (8..12)

    companion object {
        private const val PW_PATTERN = "^(?=.*\\d)(?=.*[~`!@#$%\\^&*()-])(?=.*[a-zA-Z]).{8,20}$"
    }
}
