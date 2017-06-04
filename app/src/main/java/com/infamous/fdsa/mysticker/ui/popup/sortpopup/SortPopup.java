package com.infamous.fdsa.mysticker.ui.popup.sortpopup;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.infamous.fdsa.mysticker.R;
import com.infamous.fdsa.mysticker.common.myinterface.ICallBackFromPopup;
import com.infamous.fdsa.mysticker.ui.popup.sortpopup.fragment.SortByColorFragment;
import com.infamous.fdsa.mysticker.ui.popup.sortpopup.fragment.SortByDateFragment;

/**
 * Created by apple on 5/22/17.
 */

public class SortPopup extends DialogFragment implements TabLayout.OnTabSelectedListener, ViewPager.OnPageChangeListener {

    Context context;
    View view;
    ViewPager view_pager;
    SortByColorFragment sortByColorFragment;
    SortByDateFragment sortByDateFragment;
    TabLayout tabLayout;
    TabLayout.Tab tabColor;
    TabLayout.Tab tabDate;

    ICallBackFromPopup iCallBackFromPopup;

    public SortPopup() {
        super();
        sortByColorFragment = new SortByColorFragment();
        sortByDateFragment = new SortByDateFragment();

        sortByColorFragment.setSortPopup(this);
        sortByDateFragment.setSortPopup(this);
    }

    public void setiCallBackFromPopup(ICallBackFromPopup iCallBackFromPopup) {
        this.iCallBackFromPopup = iCallBackFromPopup;
        sortByDateFragment.setCallBackFromPopup(iCallBackFromPopup);
        sortByColorFragment.setCallBackFromPopup(iCallBackFromPopup);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.layout_sort_popup, container, false);

        tabLayout = (TabLayout) v.findViewById(R.id.tab_layout);
        tabColor = tabLayout.getTabAt(0);
        tabDate = tabLayout.getTabAt(1);

        view_pager = (ViewPager) v.findViewById(R.id.view_pager);
        view_pager.setAdapter(new myTabHomeFragmentAdapter(getChildFragmentManager()));

        view_pager.addOnPageChangeListener(this);
        tabLayout.addOnTabSelectedListener(this);

        return v;
    }

    @Override
    public void onTabSelected(TabLayout.Tab tab) {
        switch (tab.getPosition()) {
            case 0:
                view_pager.setCurrentItem(0);
                break;
            case 1:
                view_pager.setCurrentItem(1);
                break;
        }
    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {

    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {

    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        if (position == 0) {
            tabColor.select();
        } else if (position == 1) {
            tabDate.select();
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    class myTabHomeFragmentAdapter extends FragmentPagerAdapter {
        public myTabHomeFragmentAdapter(FragmentManager fm) {
            super(fm);
        }

        public int getCount() {
            return 2;
        }

        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    return sortByColorFragment;
                default:
                    return sortByDateFragment;
            }

        }
    }
}
