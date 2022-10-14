package org.sopt.sample.data.entity

abstract class Follower {
    abstract val id: Int
    override fun equals(other: Any?): Boolean {
        return super.equals(other)
    }

    override fun hashCode(): Int {
        return super.hashCode()
    }
}
