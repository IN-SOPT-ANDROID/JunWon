package org.sopt.sample.presentation.sign_up

import android.os.Bundle
import androidx.activity.viewModels
import dagger.hilt.android.AndroidEntryPoint
import org.sopt.sample.R
import org.sopt.sample.databinding.ActivitySignUpBinding
import org.sopt.sample.presentation.sign_in.SignInActivity.Companion.USER_INFO
import org.sopt.sample.util.binding.BindingActivity

@AndroidEntryPoint
class SignUpActivity : BindingActivity<ActivitySignUpBinding>(R.layout.activity_sign_up) {
    private val viewModel by viewModels<SignUpViewModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.vm = viewModel
        setContentView(binding.root)
        initClickListener()
    }

    private fun initClickListener() {
        binding.btnSignup.setOnClickListener {
            viewModel.saveUserInfo()
            navigateSignInActivity()
        }
    }

    private fun navigateSignInActivity() {
        intent.apply {
            val userIndo = viewModel.getUserInfo()
            putExtra(USER_INFO, userIndo)
        }.also { intent ->
            setResult(RESULT_OK, intent)
            if (!isFinishing) finish()
        }
    }
}
