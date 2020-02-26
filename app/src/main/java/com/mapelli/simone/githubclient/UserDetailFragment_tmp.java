package com.mapelli.simone.githubclient;

import android.app.Activity;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;


import com.google.android.material.tabs.TabItem;
import com.google.android.material.tabs.TabLayout;
import com.mapelli.simone.githubclient.dummy.DummyContent;

/**
 * A fragment representing a single Item detail screen.
 * This fragment is either contained in a {@link SearchUsersActivity}
 * in two-pane mode (on tablets) or a {@link UserDetailActivity}
 * on handsets.
 */
public class UserDetailFragment_tmp extends Fragment {

    TabLayout tabLayout;
    ViewPager viewPager;
    DetailSectionAdapter detailSectionAdapter;
    TabItem tabProfile;
    TabItem tabRepositories;

    Activity activity;

    /**
     * The fragment argument representing the item ID that this fragment
     * represents.
     */
    public static final String ARG_ITEM_ID = "item_id";

    /**
     * The dummy content this fragment is presenting.
     */
    private DummyContent.DummyItem mItem;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public UserDetailFragment_tmp() {}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        activity = this.getActivity();

        if (getArguments().containsKey(ARG_ITEM_ID)) {
            // Load the dummy content specified by the fragment
            // arguments. In a real-world scenario, use a Loader
            // to load content from a content provider.
            mItem = DummyContent.ITEM_MAP.get(getArguments().getString(ARG_ITEM_ID));


            Toolbar toolbar = activity.findViewById(R.id.detail_toolbar);
            if (toolbar != null) {
                toolbar.setTitle(mItem.content);
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.user_detail_fragment, container, false);

        // Show the dummy content as text in a TextView.
        /*
        if (mItem != null) {
            ((TextView) rootView.findViewById(R.id.item_detail)).setText(mItem.details);
        }

        */

        tabLayout = rootView.findViewById(R.id.tablayout);
        tabProfile = rootView.findViewById(R.id.tabProfile);
        tabRepositories = rootView.findViewById(R.id.tabRepositories);

        viewPager = rootView.findViewById(R.id.viewPager);




        // detailSectionAdapter = new DetailSectionAdapter(getSupportFragmentManager(), tabLayout.getTabCount());
        viewPager.setAdapter(detailSectionAdapter);


        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));



        return rootView;
    }
}
