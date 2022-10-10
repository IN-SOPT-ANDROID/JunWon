package org.sopt.sample.util.extension

fun Int.isRange(minValue: Int, maxValue: Int): Boolean =
    this in (minValue..maxValue)
