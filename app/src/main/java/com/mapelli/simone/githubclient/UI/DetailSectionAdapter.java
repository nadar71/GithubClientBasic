package com.mapelli.simone.githubclient.UI;


import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class DetailSectionAdapter extends FragmentPagerAdapter {
    private static final String TAG = DetailSectionAdapter.class.getSimpleName();
    private int numOfTabs;

    DetailSectionAdapter(FragmentManager fm, int numOfTabs) {
        super(fm);
        this.numOfTabs = numOfTabs;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                ProfileFragment frag1 = new ProfileFragment();
                return frag1;

            case 1:
                RepositoriesFragment frag2 = new RepositoriesFragment();
                return frag2;

            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return numOfTabs;
    }

}
