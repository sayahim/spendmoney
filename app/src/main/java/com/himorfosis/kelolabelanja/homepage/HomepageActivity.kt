package com.himorfosis.kelolabelanja.homepage

import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import androidx.appcompat.app.ActionBar
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.himorfosis.kelolabelanja.R
import com.himorfosis.kelolabelanja.homepage.home.HomeFragment
import com.himorfosis.kelolabelanja.homepage.report.ReportsFragment
import com.himorfosis.kelolabelanja.homepage.statistict.ChartView
import com.himorfosis.kelolabelanja.homepage.statistict.PieChartView
import com.himorfosis.kelolabelanja.homepage.statistict.StatisticFragment
import com.himorfosis.kelolabelanja.utilities.Util
import kotlinx.android.synthetic.main.activity_homepage.*
import kotlinx.android.synthetic.main.toolbar_homepage.*
import me.anwarshahriar.calligrapher.Calligrapher

class HomepageActivity : AppCompatActivity() {

    val TAG = "HomepageActivity"
    var fragmentActive = 0

    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.nav_home -> {

                val fragment = HomeFragment()
                replaceFragment(fragment)

                Util.log(TAG, "beranda")

                fragmentActive = 0

                return@OnNavigationItemSelectedListener true
            }
            R.id.nav_reports -> {

                val fragment = ReportsFragment()
                replaceFragment(fragment)

                Util.log(TAG, "nav_reports")

                fragmentActive = 1

                return@OnNavigationItemSelectedListener true
            }
            R.id.nav_statistic -> {

                val fragment = PieChartView()
                replaceFragment(fragment)

                Util.log(TAG, "nav_statistic")

                fragmentActive = 3

                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_homepage)

//        setToolbar()

        setFontType()

        val fragment = HomeFragment()
        replaceFragment(fragment)

        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)

    }

    private fun replaceFragment(fragment: Fragment) {

        supportFragmentManager
                .beginTransaction()
                .replace(R.id.frame, fragment, fragment.javaClass.getSimpleName())
                .commit()
    }

    private fun setToolbar() {

        val actionBar = supportActionBar
        actionBar?.setBackgroundDrawable(ColorDrawable(ContextCompat.getColor(this, R.color.white)))

        supportActionBar!!.displayOptions = ActionBar.DISPLAY_SHOW_CUSTOM
        supportActionBar!!.setCustomView(R.layout.toolbar_homepage)

        titleBar_tv.setText("Dashboard")

    }

    private fun setFontType() {

        val calligrapher = Calligrapher(this)
        calligrapher.setFont(this@HomepageActivity, Util.regularOpenSans, true)

    }

    override fun onBackPressed() {

        if (fragmentActive == 0) {

            finishAffinity()

        } else {

            navigation.setSelectedItemId(R.id.nav_home)
            fragmentActive = 0

            val fragment = HomeFragment()
            replaceFragment(fragment)

        }

    }

}
