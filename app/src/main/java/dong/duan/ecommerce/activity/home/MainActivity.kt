package dong.duan.ecommerce.activity.home

import android.view.View
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import dong.duan.ecommerce.R
import dong.duan.ecommerce.databinding.ActivityMainBinding
import dong.duan.ecommerce.fragment.main.CartFragment
import dong.duan.ecommerce.fragment.main.FragmentAccount
import dong.duan.ecommerce.fragment.main.HomeFragment
import dong.duan.ecommerce.fragment.main.NotificationFragment
import dong.duan.ecommerce.fragment.main.SearchFragment
import dong.duan.ecommerce.fragment.other.FavoriteFragment
import dong.duan.ecommerce.interfaces.SearchManufactData
import dong.duan.ecommerce.library.base.BaseActivity
import dong.duan.ecommerce.model.Manufacturer

class MainActivity : BaseActivity<ActivityMainBinding>() {
    override fun getViewBinding() = ActivityMainBinding.inflate(layoutInflater)

    override fun createView() {
        openFragment(HomeFragment(), true)
        binding.bottomNavigation.setOnNavigationItemSelectedListener(OnItemSelectedBottomBar)
        binding.icFavorite.setOnClickListener {
            replaceFragment(FavoriteFragment(), addToBackStack = true)
        }
        HomeFragment.searchByManufact(object : SearchManufactData {
            override fun onHomeSearch(manufacturer: Manufacturer) {
                openFragment(SearchFragment(manufacturer))
                handleSpecificAction()
            }
        })

        binding.icSearch.setOnClickListener {
            binding.bottomNavigation.menu.findItem(R.id.ic_search)?.isChecked = true
            openFragment(SearchFragment(null,binding.edtSearch.text.toString()))
            handleSpecificAction()
        }
    }

    fun openFragment(fragment: Fragment, value: Boolean = false) {
        if (value) {
            binding.frameView1.visibility = View.VISIBLE
            binding.frameView2.visibility = View.GONE
            replaceFragment(fragment, R.id.frame_view1, true)
        } else {
            binding.frameView1.visibility = View.GONE
            binding.frameView2.visibility = View.VISIBLE
            replaceFragment(fragment, R.id.frame_view2, true)
        }
    }

    private val OnItemSelectedBottomBar =
        BottomNavigationView.OnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.iconhome -> {
                    openFragment(HomeFragment(), true)
                    return@OnNavigationItemSelectedListener true
                }

                R.id.ic_search -> {
                    openFragment(SearchFragment())
                    return@OnNavigationItemSelectedListener true
                }

                R.id.ic_card -> {
                    openFragment(CartFragment())
                    return@OnNavigationItemSelectedListener true
                }

                R.id.ic_discount -> {
                    openFragment(NotificationFragment())
                    return@OnNavigationItemSelectedListener true
                }

                R.id.ic_account -> {
                    openFragment(FragmentAccount())
                    return@OnNavigationItemSelectedListener true
                }
            }
            true
        }



    private fun handleSpecificAction() {
        binding.bottomNavigation.menu.findItem(R.id.ic_search)?.isChecked = true
    }

}
