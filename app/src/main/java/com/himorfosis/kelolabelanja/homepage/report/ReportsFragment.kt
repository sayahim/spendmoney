package com.himorfosis.kelolabelanja.homepage.report

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.himorfosis.kelolabelanja.R
import kotlinx.android.synthetic.main.tablayout_double.*

class ReportsFragment: Fragment() {

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

    }

    private fun setTablayoutAction() {

        tab_one_fl.setOnClickListener {

            titletab_one_tv.setTextColor(resources.getColor(R.color.white))
            tab_one_fl.setBackgroundResource(R.drawable.border_blue_dark)

            // unselected

            titletab_two_tv.setTextColor(resources.getColor(R.color.blue_dark))
            tab_two_fl.setBackgroundResource(0)

        }

        tab_two_fl.setOnClickListener {

            titletab_two_tv.setTextColor(resources.getColor(R.color.white))
            tab_two_fl.setBackgroundResource(R.drawable.border_blue_dark)

            // unselected
            titletab_one_tv.setTextColor(resources.getColor(R.color.blue_dark))
            tab_one_fl.setBackgroundResource(0)

        }


    }

}