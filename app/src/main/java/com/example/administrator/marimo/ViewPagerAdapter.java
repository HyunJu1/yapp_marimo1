package com.example.administrator.marimo;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

public class ViewPagerAdapter extends FragmentStatePagerAdapter {
    Fragment[] fragments = new Fragment[3];

    public ViewPagerAdapter(FragmentManager fm){
        super(fm);
        fragments[0] = new Onboarding1();
        fragments[1] = new Onboarding2();
        fragments[2] = new Onboarding3();
    }

    public Fragment getItem(int arg0){
        return fragments[arg0];
    }

    public int getCount() {
        return fragments.length;
    }


}
