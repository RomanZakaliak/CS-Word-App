package com.example.CSWordApp.ui.main;

import android.content.Context;

import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.CSWordApp.FragmentRule1;
import com.example.CSWordApp.FragmentRule2;
import com.example.CSWordApp.FragmentRule3;
import com.example.CSWordApp.FragmentRule4;
import com.example.CSWordApp.FragmentRule5;
import com.example.CSWordApp.FragmentRule6;
import com.example.CSWordApp.R;

/**
 * A [FragmentPagerAdapter] that returns a fragment corresponding to
 * one of the sections/tabs/pages.
 */
public class SectionsPagerAdapter extends FragmentPagerAdapter {

    @StringRes
    private static final int[] TAB_TITLES = new int[]{R.string.tab_text_1, R.string.tab_text_2, R.string.tab_text_3, R.string.tab_text_4, R.string.tab_text_5,  R.string.tab_text_6};
    private final Context mContext;

    public SectionsPagerAdapter(Context context, FragmentManager fm) {
        super(fm);
        mContext = context;
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment = null;
        switch (position){
            case 0:
                fragment = new FragmentRule1();
                break;
            case 1:
                fragment = new FragmentRule2();
                break;
            case 2:
                fragment = new FragmentRule3();
                break;
            case 3:
                fragment = new FragmentRule4();
                break;
            case 4:
                fragment = new FragmentRule5();
                break;
            case 5:
                fragment = new FragmentRule6();
                break;
        }
        return fragment;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return mContext.getResources().getString(TAB_TITLES[position]);
    }

    @Override
    public int getCount() {
        // Show n total pages.
        return 6;
    }
}