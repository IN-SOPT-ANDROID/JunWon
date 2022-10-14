package org.sopt.sample.presentation.my_page

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import androidx.fragment.app.replace
import dagger.hilt.android.AndroidEntryPoint
import org.sopt.sample.R
import org.sopt.sample.databinding.ActivityMyPageBinding
import org.sopt.sample.presentation.my_page.gallery.GalleryFragment
import org.sopt.sample.presentation.my_page.home.HomeFragment
import org.sopt.sample.presentation.my_page.search.SearchFragment
import org.sopt.sample.util.binding.BindingActivity
import timber.log.Timber

@AndroidEntryPoint
class MyPageActivity : BindingActivity<ActivityMyPageBinding>(R.layout.activity_my_page) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initDefaultFragment()
        initClickListener()
    }

    private fun initDefaultFragment() {
        navigateTo<HomeFragment>()
    }

    private fun initClickListener() {
        with(binding.botNavMain) {
            setOnItemSelectedListener {
                checkItemId(it.itemId)
            }
            setOnItemReselectedListener {
                val homeFragment =
                    supportFragmentManager.findFragmentByTag(HomeFragment::class.java.canonicalName) as? HomeFragment
                homeFragment?.initScrollToPosition(0)
                    ?: Timber.e(getString(R.string.null_point_exception))
            }
        }
    }

    private fun checkItemId(itemId: Int): Boolean {
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
        }
    }
}
