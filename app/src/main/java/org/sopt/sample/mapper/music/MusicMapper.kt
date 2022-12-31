package org.sopt.sample.mapper.music

import org.sopt.sample.data.entity.Music
import org.sopt.sample.data.model.response.MusicResponse
import org.sopt.sample.mapper.BaseMapper
import javax.inject.Inject

class MusicMapper @Inject constructor() : BaseMapper<MusicResponse, Music> {
    override fun map(from: MusicResponse): Music = Music(from.id, from.image, from.title, from.singer)
}
