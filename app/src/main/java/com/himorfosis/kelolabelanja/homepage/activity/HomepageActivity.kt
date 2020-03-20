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
import com.himorfosis.kelolabelanja.utilities.Util
import kotlinx.android.synthetic.main.activity_homepage.*

class HomepageActivity : AppCompatActivity() {

    val TAG = "HomepageActivity"
    var fragmentActive = 0

//    var mOnNavigationItemSelectedListener: BottomNavigationView? = null

    private val  mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.nav_home -> {

                val fragment = HomeFragment()
                replaceFragment(fragment)

                Util.log(TAG, "beranda")

                fragmentActive = 0

                return@OnNavigationItemSelectedListener true
            }
            R.id.nav_reports -> {

                val fragment = ChartMain()
                replaceFragment(fragment)

                Util.log(TAG, "nav_reports")

                fragmentActive = 1

                return@OnNavigationItemSelectedListener true
            }
            R.id.nav_category -> {

                val fragment = CategoryMain()
                replaceFragment(fragment)

                fragmentActive = 2

                return@OnNavigationItemSelectedListener true
            }

            R.id.nav_user -> {

                val fragment = ProfileFragment()
                replaceFragment(fragment)

                fragmentActive = 3

                return@OnNavigationItemSelectedListener true

            }
        }
        false
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_homepage)

        setFontType()

        setBottomBar()

        val fragment = HomeFragment()
        replaceFragment(fragment)

    }

    override fun onStart() {
        super.onStart()


    }

    private fun setBottomBar() {

        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)

    }

    private fun replaceFragment(fragment: Fragment) {

        supportFragmentManager
                .beginTransaction()
                .replace(R.id.frame, fragment, fragment.javaClass.getSimpleName())
                .commit()
    }


    private fun setFontType() {

//        val calligrapher = Calligrapher(this)
//        calligrapher.setFont(this@HomepageActivity, Util.regularOpenSans, true)

    }

    override fun onBackPressed() {

        if (fragmentActive == 0) {

            finishAffinity()

        } else {

            navigation.selectedItemId = R.id.nav_home
            fragmentActive = 0

            val fragment = HomeFragment()
            replaceFragment(fragment)

        }

    }

}
