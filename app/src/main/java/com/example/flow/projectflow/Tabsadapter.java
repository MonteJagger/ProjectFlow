package com.example.flow.projectflow;

/**
 * Created by Dennis on 4/27/2015.
 */

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

public class Tabsadapter extends FragmentStatePagerAdapter {

    private int TOTAL_TABS = 2;

    public Tabsadapter(FragmentManager fm) {
        super(fm);
        //  Auto-generated constructor stub
    }

    @Override
    public Fragment getItem(int index) {
        //  Auto-generated method stub
        switch (index) {
            case 0:
                return new ViewCoursesFragment();

            case 1:
                return new BrowseFragment();

        }

        return null;
    }

    @Override
    public int getCount() {
        // Auto-generated method stub
        return TOTAL_TABS;
    }

}
