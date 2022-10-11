package org.sopt.sample.presentation.my_page.home

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import org.sopt.sample.R
import org.sopt.sample.databinding.FragmentHomeBinding
import org.sopt.sample.presentation.my_page.home.adapter.FollowerAdapter
import org.sopt.sample.presentation.setting.SettingActivity
import org.sopt.sample.util.binding.BindingFragment
import org.sopt.sample.util.extension.repeatOnStarted
import timber.log.Timber

@AndroidEntryPoint
class HomeFragment : BindingFragment<FragmentHomeBinding>(R.layout.fragment_home) {

    private val viewModel by viewModels<HomeViewModel>()
    private val followerAdapter by lazy { FollowerAdapter() }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.vm = viewModel
        initAdapter()
        observeUiState()
        initClickListener()
    }

    private fun initAdapter() {
        binding.rvFollowers.run {
            adapter = followerAdapter
            itemAnimator = null
        }
    }

    fun initScrollToPosition(targetPos: Int) {
        binding.rvFollowers.layoutManager?.scrollToPosition(targetPos)
            ?: Timber.e(getString(R.string.null_point_exception))
    }

    private fun observeUiState() {
        repeatOnStarted {
            viewModel.uiState.collect { uiState ->
                followerAdapter.submitList(uiState.followerList)
            }
        }
    }

    private fun initClickListener() {
        binding.ivSetting.setOnClickListener {
            activity?.let { myPageActivity ->
                startActivity(Intent(myPageActivity, SettingActivity::class.java))
            } ?: Timber.e(getString(R.string.null_point_exception))
        }
    }
}
