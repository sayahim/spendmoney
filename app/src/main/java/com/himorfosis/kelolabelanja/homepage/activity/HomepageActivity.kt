package com.himorfosis.kelolabelanja.homepage.activity

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.himorfosis.kelolabelanja.R
import com.himorfosis.kelolabelanja.app.MyApp
import com.himorfosis.kelolabelanja.auth.Login
import com.himorfosis.kelolabelanja.homepage.category.CategoryMain
import com.himorfosis.kelolabelanja.homepage.chart.ChartMain
import com.himorfosis.kelolabelanja.homepage.home.HomeFragment
import com.himorfosis.kelolabelanja.homepage.profile.ProfileFragment
import com.himorfosis.kelolabelanja.state.HomeState
import com.himorfosis.kelolabelanja.utilities.Util
import com.himorfosis.kelolabelanja.utilities.preferences.AccountPref
import com.himorfosis.kelolabelanja.utilities.preferences.AppPreferences
import com.himorfosis.kelolabelanja.utilities.preferences.DataPreferences
import kotlinx.android.synthetic.main.activity_homepage.*

class HomepageActivity : AppCompatActivity() {

    companion object {
        val TAG = "HomepageActivity"
        var fragmentActive = 0
        val NAV_HOME = 0
        val NAV_REPORT = 1
        val NAV_CATEGORY = 2
        val NAV_PROFIL = 3

    }

    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.nav_home -> {
                if (fragmentActive != NAV_HOME) {
                    homeFragment()
                    return@OnNavigationItemSelectedListener true
                }
            }
            R.id.nav_reports -> {
                if (fragmentActive != NAV_REPORT) {
                    reportFragment()
                    return@OnNavigationItemSelectedListener true
                }
            }
            R.id.nav_category -> {
                if (fragmentActive != NAV_CATEGORY) {
                    categoryFragment()
                    return@OnNavigationItemSelectedListener true
                }
            }

            R.id.nav_profile -> {
                if (fragmentActive != NAV_PROFIL) {
                    profileFragment()
                    return@OnNavigationItemSelectedListener true
                }
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

        val getFrom = intent.getStringExtra("from")
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
        fragmentActive = NAV_HOME
        val fragment = HomeFragment()
        replaceFragment(fragment)
        navigation.menu.findItem(R.id.nav_home).isChecked = true

    }

    private fun reportFragment() {
        fragmentActive = NAV_REPORT
        val fragment = ChartMain()
        replaceFragment(fragment)
        navigation.menu.findItem(R.id.nav_reports).isChecked = true

    }

    private fun categoryFragment() {
        fragmentActive = NAV_CATEGORY
        val fragment = CategoryMain()
        replaceFragment(fragment)
        navigation.menu.findItem(R.id.nav_category).isChecked = true

    }

    private fun profileFragment() {

        val getToken = DataPreferences.account.getString(AccountPref.TOKEN)

        if (getToken!!.isEmpty()) {
            startActivity(Intent(this, Login::class.java))
        } else {
            fragmentActive = NAV_PROFIL
            val fragment = ProfileFragment()
            replaceFragment(fragment)
            navigation.menu.findItem(R.id.nav_profile).isChecked = true
        }
    }

    private fun replaceFragment(fragment: Fragment) {
        Util.log(TAG, "fragment : $fragment")
        supportFragmentManager
                .beginTransaction()
                .replace(R.id.frame, fragment, fragment.javaClass.simpleName)
                .commit()
    }

    override fun onBackPressed() {
        if (fragmentActive == NAV_HOME) {
            finishAffinity()
        } else {
            fragmentActive = NAV_HOME
            homeFragment()
        }
    }

    private fun isLog(message: String) {
        Util.log(TAG, message)
    }

}
