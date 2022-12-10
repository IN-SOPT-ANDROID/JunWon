package org.sopt.sample.presentation.my_page.gallery

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import okhttp3.MultipartBody
import org.sopt.sample.data.entity.Music
import org.sopt.sample.data.repository.MusicRepository
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class GalleryViewModel @Inject constructor(
    private val musicRepository: MusicRepository
) : ViewModel() {
    private val _uiState: MutableStateFlow<List<Music>> = MutableStateFlow(emptyList())
    val uiState = _uiState.asStateFlow()
    private val _musicImg: MutableStateFlow<Uri> = MutableStateFlow(Uri.EMPTY)
    val musicImg = _musicImg.asStateFlow()

    init {
        getMusics()
    }

    fun setUri(img: Uri) {
        _musicImg.value = img
    }

    fun postMusic(image: MultipartBody.Part) {
        viewModelScope.launch {
            musicRepository.postMusic(
                title = "Hous",
                singer = "JUNWON",
                image = image
            )
                .onSuccess {
                    getMusics()
                }
                .onFailure { Timber.e(it) }
        }
    }

    private fun getMusics() {
        viewModelScope.launch {
            musicRepository.getMusics()
                .onSuccess { musics ->
                    _uiState.value = musics
                }
                .onFailure { Timber.e(it) }
        }
    }
}
