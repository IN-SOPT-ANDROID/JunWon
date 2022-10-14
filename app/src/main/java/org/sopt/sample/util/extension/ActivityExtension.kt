package org.sopt.sample.util.extension

import androidx.annotation.IdRes
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import androidx.fragment.app.replace

/**
 * Framgent T로 replace
 *
 * @param T Fragment
 * @param fragmentContainerId Int
 * @param tag String
 * @param action () -> Unit
 */
inline fun <reified T : Fragment> AppCompatActivity.replaceTo(
    @IdRes fragmentContainerId: Int,
    tag: String? = null,
    action: () -> Unit = {}
) {
    supportFragmentManager.commit {
        replace<T>(fragmentContainerId, tag)
        action()
        setReorderingAllowed(true)
    }
}

/**
 * Framgent T로 replace하면서 addToBackStack
 *
 * @param T Fragment
 * @param fragmentContainerId Int
 * @param tag String
 * @param name String
 * @param action () -> Unit
 */
inline fun <reified T : Fragment> AppCompatActivity.replaceAndAddToBackStack(
    @IdRes fragmentContainerId: Int,
    tag: String? = null,
    name: String? = null,
    action: () -> Unit = {}
) {
    supportFragmentManager.commit {
        replace<T>(fragmentContainerId, tag)
        addToBackStack(name)
        action()
        setReorderingAllowed(true)
    }
}
