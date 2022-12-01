package org.sopt.sample.presentation.sign_in

import android.content.Intent
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import dagger.hilt.android.AndroidEntryPoint
import org.sopt.sample.R
import org.sopt.sample.databinding.ActivitySignInBinding
import org.sopt.sample.presentation.my_page.MainActivity
import org.sopt.sample.presentation.sign_up.SignUpActivity
import org.sopt.sample.util.binding.BindingActivity
import org.sopt.sample.util.extension.repeatOnStarted
import org.sopt.sample.util.extension.showSnackBar
import org.sopt.sample.util.showToast

@AndroidEntryPoint
class SignInActivity : BindingActivity<ActivitySignInBinding>(R.layout.activity_sign_in) {
    private val viewModel by viewModels<SignInViewModel>()
    private val resultLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == RESULT_OK) {
                showSnackBar(binding.root, getString(R.string.sign_up_success)) {
                    setAction("확인") {}
                }
                viewModel.setSignInContent()
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.vm = viewModel
        checkAutoLogin()
        initClickListener()
        collectSignInState()
    }

    private fun collectSignInState() {
        repeatOnStarted {
            viewModel.isSignInSuccess.collect { isSuccess ->
                if (isSuccess) {
                    return@collect navigateMainActivity()
                }
                showSnackBar(binding.root, getString(R.string.sign_in_fail)) {
                    setAction("확인") {}
                }
            }
        }
    }

    private fun checkAutoLogin() {
        if (viewModel.isAutoMode.value) {
            startActivity(Intent(this, MainActivity::class.java))
            if (!isFinishing) finish()
        }
    }

    private fun initClickListener() {
        binding.btnSignup.setOnClickListener {
            navigateSignUpActivity()
        }
    }

    private fun navigateMainActivity() {
        startActivity(Intent(this, MainActivity::class.java))
        showToast(this, getString(R.string.sign_in_success))
        if (!isFinishing) finish()
    }

    private fun navigateSignUpActivity() {
        val signUpIntent = Intent(this, SignUpActivity::class.java)
        resultLauncher.launch(signUpIntent)
    }
}
