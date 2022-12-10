package org.sopt.sample.presentation.my_page.gallery

import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import coil.load
import coil.transform.RoundedCornersTransformation
import dagger.hilt.android.AndroidEntryPoint
import org.sopt.sample.R
import org.sopt.sample.databinding.FragmentGalleryBinding
import org.sopt.sample.util.ContentUriRequestBody
import org.sopt.sample.util.binding.BindingFragment
import org.sopt.sample.util.extension.repeatOnStarted

@AndroidEntryPoint
class GalleryFragment : BindingFragment<FragmentGalleryBinding>(R.layout.fragment_gallery) {
    private lateinit var musicAdapter: MusicAdapter
    private val viewModel by viewModels<GalleryViewModel>()
    private val launcherObserver by lazy {
        GalleryLauncherObserver(requireActivity().activityResultRegistry, ::imageLoad)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initLifeCycleObserver()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initClickListener()
        initAdapter()
        collectMusics()
        postBtnOnClick()
    }

    private fun postBtnOnClick() {
        binding.btnFetchImage.setOnClickListener {
            viewModel.postMusic(
                ContentUriRequestBody(
                    requireContext(),
                    viewModel.musicImg.value
                ).toFormData()
            )
        }
    }

    private fun initAdapter() {
        musicAdapter = MusicAdapter()
        binding.rvMusic.adapter = musicAdapter
    }

    private fun collectMusics() {
        repeatOnStarted {
            viewModel.uiState.collect(musicAdapter::submitList)
        }
        repeatOnStarted {
            viewModel.musicImg.collect { uri ->
                binding.ivProfile.load(uri) {
                    placeholder(R.drawable.my_profile)
                        .transformations(RoundedCornersTransformation(50f))
                        .crossfade(true)
                        .error(R.drawable.ic_error_24)
                }
            }
        }
    }

    private fun initLifeCycleObserver() {
        lifecycle.addObserver(launcherObserver)
    }

    private fun initClickListener() {
        binding.ivProfile.setOnClickListener {
            launcherObserver.selectImage()
        }
    }

    private fun imageLoad(uri: Uri) {
        viewModel.setUri(uri)
    }
}
