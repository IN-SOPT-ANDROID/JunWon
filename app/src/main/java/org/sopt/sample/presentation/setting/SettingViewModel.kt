package org.sopt.sample.presentation.setting

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import org.sopt.sample.data.repository.DefaultAuthRepository
import javax.inject.Inject

@HiltViewModel
class SettingViewModel @Inject constructor(private val authRepository: DefaultAuthRepository) :
    ViewModel() {

    fun logOut() {
        authRepository.clearPreference()
    }
}
