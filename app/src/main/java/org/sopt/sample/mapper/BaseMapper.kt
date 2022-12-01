package org.sopt.sample.mapper

interface BaseMapper<F, T> {
    fun map(from: F): T
}
