package org.sopt.sample.presentation.sign_up

import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import org.sopt.sample.R
import org.sopt.sample.databinding.ActivitySignUpBinding
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
            lifecycleScope.launch {
                viewModel.postSignUp().join()
                navigateSignInActivity()
            }
        }
    }

    private fun navigateSignInActivity() {
        setResult(RESULT_OK, intent)
        if (!isFinishing) finish()
    }
}
