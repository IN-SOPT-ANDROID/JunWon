package org.sopt.sample.presentation.setting

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import org.sopt.sample.data.local.SoptSharedPreference
import javax.inject.Inject

@HiltViewModel
class SettingViewModel @Inject constructor(private val soptSharedPreference: SoptSharedPreference) :
    ViewModel() {

    fun cancelAutoLogin() {
        soptSharedPreference.isAutoLogin = false
    }
}
