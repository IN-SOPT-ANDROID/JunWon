package org.sopt.sample.util.extension

fun String.isLetters(): Boolean =
    this.all { it.isLetter() }
