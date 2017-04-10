package bigbody.project.com.reclife.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;

/**
 * Created by shuttlemacmini on 2017/2/23.
 */

public class CustomFragmentPageAdapter extends FragmentPagerAdapter {

    private ArrayList<Fragment> mFragments;
    private String[] Titles;

    public CustomFragmentPageAdapter(FragmentManager fm,ArrayList<Fragment> fragments,String[] titles) {
        super(fm);
        this.mFragments = fragments;
        this.Titles = titles;
    }

    @Override
    public Fragment getItem(int position) {
        return mFragments.get(position);
    }


    @Override
    public CharSequence getPageTitle(int position) {
        return Titles[position];
    }


    @Override
    public int getCount() {
        return mFragments.size();
    }







}
