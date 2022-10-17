package org.sopt.sample.presentation.my_page

import android.os.Bundle
import androidx.activity.addCallback
import dagger.hilt.android.AndroidEntryPoint
import org.sopt.sample.R
import org.sopt.sample.databinding.ActivityMainBinding
import org.sopt.sample.presentation.my_page.gallery.GalleryFragment
import org.sopt.sample.presentation.my_page.home.HomeFragment
import org.sopt.sample.presentation.my_page.home.HomeFragment.Companion.HOME_FRAGMENT_TAG
import org.sopt.sample.presentation.my_page.search.SearchFragment
import org.sopt.sample.util.binding.BindingActivity
import org.sopt.sample.util.extension.navigateTo
import org.sopt.sample.util.showToast
import timber.log.Timber
import kotlin.system.exitProcess

@AndroidEntryPoint
class MainActivity : BindingActivity<ActivityMainBinding>(R.layout.activity_main) {

    private var curPos = HOME
    private var onBackPressedTime = 0L

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initNavigation()
        initClickListener()
        initBackButtonClickListener()
    }

    private fun initNavigation() {
        navigateTo<HomeFragment>(R.id.fc_my_page, HOME_FRAGMENT_TAG)
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

    private fun initBackButtonClickListener() {
        onBackPressedDispatcher.addCallback {
            if (curPos == HOME) {
                val curTime = System.currentTimeMillis()
                val gap = curTime - onBackPressedTime
                if (gap > 1500) {
                    onBackPressedTime = curTime
                    showToast(this@MainActivity, "한 번 더 누르면 종료")
                    return@addCallback
                }
                finish()
                System.runFinalization()
                exitProcess(0)
            }
            updateBottomMenu()
        }
    }

    private fun onNavigationItemSelected(itemId: Int): Boolean {
        when (itemId) {
            R.id.navigation_home -> navigateTo<HomeFragment>(R.id.fc_my_page, HOME_FRAGMENT_TAG) {
                curPos = HOME
            }
            R.id.navigation_gallery -> navigateTo<GalleryFragment>(R.id.fc_my_page) {
                curPos = GALLERY
            }
            R.id.navigation_search -> navigateTo<SearchFragment>(R.id.fc_my_page) {
                curPos = SEARCH
            }
            else -> Timber.e(IllegalArgumentException("itemId: $itemId"))
        }
        return true
    }

    private fun updateBottomMenu() {
        navigateTo<HomeFragment>(R.id.fc_my_page) {
            curPos = HOME
        }
        binding.botNavMain.selectedItemId = R.id.navigation_home
    }

    companion object {
        private const val HOME = 0
        private const val GALLERY = 1
        private const val SEARCH = 2
    }
}
