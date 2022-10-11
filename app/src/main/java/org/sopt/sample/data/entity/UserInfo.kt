package org.sopt.sample.data.entity

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class UserInfo(
    val id: String = "",
    val password: String = "",
    val mbti: String = ""
) : Parcelable
