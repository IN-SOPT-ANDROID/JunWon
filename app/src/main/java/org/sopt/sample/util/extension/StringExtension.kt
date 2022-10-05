package org.sopt.sample.util.extension

fun String.isLetterOrDigit(): Boolean =
    this.all { it.isLetterOrDigit() }
