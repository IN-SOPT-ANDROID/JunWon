package org.sopt.sample.presentation.my_page

import android.os.Bundle
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import androidx.fragment.app.replace
import dagger.hilt.android.AndroidEntryPoint
import org.sopt.sample.R
import org.sopt.sample.databinding.ActivityMyPageBinding
import org.sopt.sample.presentation.my_page.gallery.GalleryFragment
import org.sopt.sample.presentation.my_page.home.HomeFragment
import org.sopt.sample.presentation.my_page.home.HomeFragment.Companion.HOME_FRAGMENT_TAG
import org.sopt.sample.presentation.my_page.search.SearchFragment
import org.sopt.sample.util.binding.BindingActivity
import org.sopt.sample.util.showToast
import timber.log.Timber

@AndroidEntryPoint
class MyPageActivity : BindingActivity<ActivityMyPageBinding>(R.layout.activity_my_page) {

    private var hasHomeFragmentInBackStack = false
    private var onBackPressedTime = 0L
    private val onBackPressedCallback by lazy {
        object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                if (supportFragmentManager.backStackEntryCount == 0) {
                    val curTime = System.currentTimeMillis()
                    val gap = curTime - onBackPressedTime
                    if (gap > 1500) {
                        onBackPressedTime = curTime
                        showToast(this@MyPageActivity, "한 번 더 누르면 종료")
                        return
                    }
                    finish()
                }
                supportFragmentManager.popBackStackImmediate()
                hasHomeFragmentInBackStack = false
                updateBottomMenu()
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initDefaultFragment()
        initClickListener()
        addActionWhenBackPressed()
    }

    private fun initDefaultFragment() {
        supportFragmentManager.commit {
            replace<HomeFragment>(R.id.fc_my_page, HOME_FRAGMENT_TAG)
        }
    }

    private fun initClickListener() {
        with(binding.botNavMain) {
            setOnItemSelectedListener {
                onNavigationItemSelected(it.itemId)
            }
            setOnItemReselectedListener {
                val homeFragment =
                    supportFragmentManager.findFragmentByTag(HomeFragment::class.java.canonicalName) as? HomeFragment
                homeFragment?.let { fragment ->
                    if (fragment.isVisible) {
                        fragment.initScrollToPosition(0)
                        return@let
                    }
                    Timber.e("homeFragment is InVisible")
                } ?: Timber.e(getString(R.string.null_point_exception))
            }
        }
    }

    private fun addActionWhenBackPressed() {
        onBackPressedDispatcher.addCallback(this, onBackPressedCallback)
    }

    private fun onNavigationItemSelected(itemId: Int): Boolean {
        when (itemId) {
            R.id.navigation_home -> navigateTo<HomeFragment>()
            R.id.navigation_gallery -> navigateTo<GalleryFragment>()
            R.id.navigation_search -> navigateTo<SearchFragment>()
            else -> Timber.e(IllegalArgumentException("itemId: $itemId"))
        }
        return true
    }

    private inline fun <reified T : Fragment> navigateTo() {
        supportFragmentManager.commit {
            replace<T>(R.id.fc_my_page, T::class.java.canonicalName)
            if (!hasHomeFragmentInBackStack) {
                addToBackStack(HOME_FRAGMENT_TAG)
                hasHomeFragmentInBackStack = true
            }
        }
    }

    private fun updateBottomMenu() {
        Timber.i("Home")
        val homeFragment: Fragment? =
            supportFragmentManager.findFragmentByTag(HOME_FRAGMENT_TAG) as? HomeFragment
        homeFragment?.let { fragment ->
            if (fragment.isVisible) {
                binding.botNavMain.menu.findItem(R.id.navigation_home).isChecked = true
                return@let
            }
            Timber.e("homeFragment is InVisible")
        } ?: Timber.e(getString(R.string.null_point_exception))
    }
}
