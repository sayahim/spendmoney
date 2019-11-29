package com.himorfosis.kelolabelanja.homepage.statistict.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.himorfosis.kelolabelanja.R;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

public class StatisticFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        //Returning the layout file after inflating
        //Change R.layout.tab1 in you classes
        return inflater.inflate(R.layout.statistics_fragment, container, false);

    }

    public void onViewCreated(final View view, Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        super.onViewCreated(view, savedInstanceState);
        getActivity().invalidateOptionsMenu();

        setTablayoutAction(view);

        // replace fragment
        Fragment fragment = new StatisticSpending();
        replaceFragment(fragment);

    }

    private void setTablayoutAction(View view) {

        FrameLayout tab_one_fl = view.findViewById(R.id.tab_one_fl);
        FrameLayout tab_two_fl = view.findViewById(R.id.tab_two_fl);
        TextView titletab_one_tv = view.findViewById(R.id.titletab_one_tv);
        TextView titletab_two_tv = view.findViewById(R.id.titletab_two_tv);

        tab_one_fl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                titletab_one_tv.setTextColor(getResources().getColor(R.color.white));
                tab_one_fl.setBackgroundResource(R.drawable.border_blue_dark);

                // unselected

                titletab_two_tv.setTextColor(getResources().getColor(R.color.blue_dark));
                tab_two_fl.setBackgroundResource(0);


                // replace fragment
                Fragment fragment = new StatisticSpending();
                replaceFragment(fragment);

            }
        });

        tab_two_fl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                titletab_two_tv.setTextColor(getResources().getColor(R.color.white));
                tab_two_fl.setBackgroundResource(R.drawable.border_blue_dark);

                // unselected
                titletab_one_tv.setTextColor(getResources().getColor(R.color.blue_dark));
                tab_one_fl.setBackgroundResource(0);


                // replace fragment
                Fragment fragment = new StatisticIncome();
                replaceFragment(fragment);

            }
        });

    }

    private void replaceFragment(Fragment fragment) {

        FragmentTransaction ft = getChildFragmentManager().beginTransaction();
        ft.replace(R.id.frame_statistic, fragment);
        ft.commit();
    }

}
