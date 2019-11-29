package com.himorfosis.kelolabelanja.homepage.report.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.himorfosis.kelolabelanja.R
import com.himorfosis.kelolabelanja.utilities.Util
import kotlinx.android.synthetic.main.tablayout_double.*

class ReportsFragment: Fragment() {

    val TAG = "ReportsFragment"

    companion object {

        fun newInstance(): ReportsFragment {
            return ReportsFragment()
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.reports_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setTablayoutAction()

        val fragment = ReportsSpendingFragment()
        replaceFragment(fragment)

    }

    private fun setTablayoutAction() {

        tab_one_fl.setOnClickListener {

            titletab_one_tv.setTextColor(resources.getColor(R.color.white))
            tab_one_fl.setBackgroundResource(R.drawable.border_blue_dark)

            // unselected

            titletab_two_tv.setTextColor(resources.getColor(R.color.blue_dark))
            tab_two_fl.setBackgroundResource(0)

            val fragment = ReportsSpendingFragment()
            replaceFragment(fragment)

            Util.log(TAG, "spending ")

        }

        tab_two_fl.setOnClickListener {

            titletab_two_tv.setTextColor(resources.getColor(R.color.white))
            tab_two_fl.setBackgroundResource(R.drawable.border_blue_dark)

            // unselected
            titletab_one_tv.setTextColor(resources.getColor(R.color.blue_dark))
            tab_one_fl.setBackgroundResource(0)

            val fragment = ReportsIncomeFragment()
            replaceFragment(fragment)

            Util.log(TAG, "income ")

        }


    }

    private fun replaceFragment(fragment: Fragment) {

        childFragmentManager
                .beginTransaction()
                .replace(R.id.frame_reports, fragment, fragment.javaClass.getSimpleName())
                .commit()
    }

}