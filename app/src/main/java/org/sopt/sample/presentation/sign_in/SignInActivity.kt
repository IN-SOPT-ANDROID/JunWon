package org.sopt.sample.presentation.sign_in

import android.content.Intent
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import dagger.hilt.android.AndroidEntryPoint
import org.sopt.sample.R
import org.sopt.sample.data.model.UserInfo
import org.sopt.sample.databinding.ActivitySignInBinding
import org.sopt.sample.presentation.my_page.MyPageActivity
import org.sopt.sample.presentation.sign_up.SignUpActivity
import org.sopt.sample.util.binding.BindingActivity
import org.sopt.sample.util.extension.showSnackBar
import org.sopt.sample.util.showToast
import timber.log.Timber

@AndroidEntryPoint
class SignInActivity : BindingActivity<ActivitySignInBinding>(R.layout.activity_sign_in) {
    private val viewModel by viewModels<SignInViewModel>()
    private val resultLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == RESULT_OK) {
                showSnackBar(binding.root, getString(R.string.sign_up_success)) {
                    setAction("확인") {}
                }
                val userInfo = result.data?.getParcelableExtra<UserInfo>(USER_INFO)
                userInfo?.also { userData ->
                    viewModel.setLoginInfo(userData)
                } ?: Timber.e(getString(R.string.null_point_exception))
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.vm = viewModel
        checkAutoLogin()
        initClickListener()
    }

    private fun checkAutoLogin() {
        Timber.e("viewModel.isAutoMode.value :${viewModel.isAutoMode.value}")
        if (viewModel.isAutoMode.value) {
            startActivity(Intent(this, MyPageActivity::class.java))
            if (!isFinishing) finish()
        }
    }

    private fun initClickListener() {
        binding.btnSignup.setOnClickListener {
            navigateSignUpActivity()
        }

        binding.btnLogin.setOnClickListener {
            navigateMyPageActivity()
        }
    }

    private fun navigateMyPageActivity() {
        if (viewModel.checkLoginStatus()) {
            Timber.e("viewModel.isAutoMode :${viewModel.isAutoMode.value}")
            viewModel.saveAutoMode()
            Intent(this, MyPageActivity::class.java).also { myPageIntent ->
                startActivity(myPageIntent)
                showToast(this, getString(R.string.sign_in_success))
                if (!isFinishing) finish()
            }
        }
        showSnackBar(binding.root, getString(R.string.sign_in_fail)) {
            setAction("확인") {}
        }
    }

    private fun navigateSignUpActivity() {
        val signUpIntent = Intent(this, SignUpActivity::class.java)
        resultLauncher.launch(signUpIntent)
    }

    companion object {
        const val USER_INFO = "user_info"
    }
}
