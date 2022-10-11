package org.sopt.sample.data.entity

interface Follower {
    val id: Int
    override operator fun equals(other: Any?): Boolean
    override fun hashCode(): Int
}
