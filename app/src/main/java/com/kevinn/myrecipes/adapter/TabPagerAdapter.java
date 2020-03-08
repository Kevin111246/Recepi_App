package com.kevinn.myrecipes.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.kevinn.myrecipes.fragment.RecipesFragment;

public class TabPagerAdapter extends FragmentPagerAdapter {

    private static final int total_pages = 1;

    public TabPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new RecipesFragment();
        }
        return null;
    }

    @Override
    public int getCount() {

        return total_pages;

    }

    @Override
    public CharSequence getPageTitle(int position) {

        switch (position) {
            case 0:
                return "Recipes";
        }
        return null;
    }
}