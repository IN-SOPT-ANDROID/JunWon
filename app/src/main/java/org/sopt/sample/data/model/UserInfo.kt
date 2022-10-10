package org.sopt.sample.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class UserInfo(
    val id: String = "",
    val password: String = "",
    val mbti: String = ""
) : Parcelable
