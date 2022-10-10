package org.sopt.sample.presentation.my_page

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import dagger.hilt.android.AndroidEntryPoint
import org.sopt.sample.R
import org.sopt.sample.databinding.ActivityMyPageBinding
import org.sopt.sample.presentation.setting.SettingActivity
import org.sopt.sample.util.binding.BindingActivity

@AndroidEntryPoint
class MyPageActivity : BindingActivity<ActivityMyPageBinding>(R.layout.activity_my_page) {

    private val viewModel by viewModels<MyPageViewModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.vm = viewModel
        initClickListener()
    }

    private fun initClickListener() {
        binding.ivSetting.setOnClickListener {
            startActivity(Intent(this, SettingActivity::class.java))
        }
    }
}
