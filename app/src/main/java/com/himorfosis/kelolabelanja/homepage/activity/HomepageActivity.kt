package com.himorfosis.kelolabelanja.homepage.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.himorfosis.kelolabelanja.R
import com.himorfosis.kelolabelanja.homepage.category.CategoryMain
import com.himorfosis.kelolabelanja.homepage.chart.ChartMain
import com.himorfosis.kelolabelanja.homepage.view.HomeFragment
import com.himorfosis.kelolabelanja.homepage.view.ProfileFragment
import com.himorfosis.kelolabelanja.state.HomeState
import com.himorfosis.kelolabelanja.utilities.Util
import kotlinx.android.synthetic.main.activity_homepage.*

class HomepageActivity : AppCompatActivity() {

    val TAG = "HomepageActivity"
    var fragmentActive = 0
    val NAV_START = 0
    val NAV_ACTION = 1

//    var mOnNavigationItemSelectedListener: BottomNavigationView? = null

    private val  mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.nav_home -> {
                homeFragment()
                return@OnNavigationItemSelectedListener true
            }
            R.id.nav_reports -> {
                reportFragment()
                return@OnNavigationItemSelectedListener true
            }
            R.id.nav_category -> {
                categoryFragment()
                return@OnNavigationItemSelectedListener true
            }

            R.id.nav_profile -> {
                profileFragment()
                return@OnNavigationItemSelectedListener true

            }
        }
        false
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_homepage)

        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)

        checkParseData()

    }

    private fun checkParseData() {

        val getFrom = intent.getStringExtra("home")

        if (getFrom != null) {

            if (getFrom == HomeState.PROFILE) {
                profileFragment()
            } else if (getFrom == HomeState.REPORT) {
                reportFragment()
            } else if (getFrom == HomeState.CATEGORY) {
                categoryFragment()
            } else {
                homeFragment()
            }

        } else {
            homeFragment()
        }

    }

    private fun homeFragment() {
        fragmentActive = NAV_START
        val fragment = HomeFragment()
        replaceFragment(fragment)
        navigation.menu.findItem(R.id.nav_home).isChecked = true

    }

    private fun reportFragment() {
        fragmentActive = NAV_ACTION
        val fragment = ChartMain()
        replaceFragment(fragment)
        navigation.menu.findItem(R.id.nav_reports).isChecked = true

    }

    private fun categoryFragment() {
        fragmentActive = NAV_ACTION
        val fragment = CategoryMain()
        replaceFragment(fragment)
        navigation.menu.findItem(R.id.nav_category).isChecked = true

    }

    private fun profileFragment() {
        fragmentActive = NAV_ACTION
        val fragment = ProfileFragment()
        replaceFragment(fragment)
        navigation.menu.findItem(R.id.nav_profile).isChecked = true

    }

    private fun replaceFragment(fragment: Fragment) {
        Util.log(TAG, "fragment : $fragment")
        supportFragmentManager
                .beginTransaction()
                .replace(R.id.frame, fragment, fragment.javaClass.simpleName)
                .commit()
    }

    override fun onBackPressed() {

        if (fragmentActive == 0) {
            finishAffinity()
        } else {
            homeFragment()
        }

    }

}
