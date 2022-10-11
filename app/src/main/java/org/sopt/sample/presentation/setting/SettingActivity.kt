package org.sopt.sample.presentation.setting

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import dagger.hilt.android.AndroidEntryPoint
import org.sopt.sample.R
import org.sopt.sample.databinding.ActivitySettingBinding
import org.sopt.sample.presentation.sign_in.SignInActivity
import org.sopt.sample.util.binding.BindingActivity

@AndroidEntryPoint
class SettingActivity : BindingActivity<ActivitySettingBinding>(R.layout.activity_setting) {
    private val viewModel by viewModels<SettingViewModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.vm = viewModel
        initClickListener()
    }

    private fun initClickListener() {
        binding.tvLogout.setOnClickListener {
            Intent(this, SignInActivity::class.java).also { intent ->
                viewModel.cancelAutoLogin()
                startActivity(intent)
                finishAffinity()
            }
        }
    }
}
