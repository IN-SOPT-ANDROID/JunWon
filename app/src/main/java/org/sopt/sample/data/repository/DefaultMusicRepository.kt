package org.sopt.sample.data.repository

import okhttp3.MultipartBody
import org.sopt.sample.data.datasource.remote.MusicDataSource
import org.sopt.sample.data.entity.Music
import org.sopt.sample.mapper.music.MusicMapper
import javax.inject.Inject

class DefaultMusicRepository @Inject constructor(
    private val musicDataSource: MusicDataSource,
    private val musicMapper: MusicMapper
) : MusicRepository {
    override suspend fun getMusics(): Result<List<Music>> =
        runCatching {
            musicDataSource.getMusics().data.map {
                musicMapper.map(it)
            }
        }

    override suspend fun postMusic(
        title: String,
        singer: String,
        image: MultipartBody.Part
    ) = runCatching {
        musicMapper.map(musicDataSource.postMusic(title, singer, image).data)
    }
}
