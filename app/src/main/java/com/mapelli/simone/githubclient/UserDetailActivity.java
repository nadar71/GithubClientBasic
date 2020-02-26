package com.mapelli.simone.githubclient;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.widget.Toolbar;


import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NavUtils;
import androidx.core.content.ContextCompat;
import androidx.viewpager.widget.ViewPager;

import android.view.MenuItem;

import com.google.android.material.tabs.TabItem;
import com.google.android.material.tabs.TabLayout;


public class UserDetailActivity extends AppCompatActivity {
    Toolbar toolbar;

    TabLayout tabLayout;
    ViewPager viewPager;
    DetailSectionAdapter detailSectionAdapter;
    TabItem tabProfile;
    TabItem tabRepositories;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_detail);

        tabLayout =       findViewById(R.id.tablayout);
        tabProfile =      findViewById(R.id.tabProfile);
        tabRepositories = findViewById(R.id.tabRepositories);

        viewPager =       findViewById(R.id.viewPager);

        setupUpperBar();

        detailSectionAdapter = new DetailSectionAdapter(getSupportFragmentManager(), tabLayout.getTabCount());
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



        /*
        if (savedInstanceState == null) {
            Bundle arguments = new Bundle();
            arguments.putString(UserDetailFragment_tmp.ARG_ITEM_ID,
                    getIntent().getStringExtra(UserDetailFragment_tmp.ARG_ITEM_ID));

            UserDetailFragment_tmp fragment = new UserDetailFragment_tmp();
            fragment.setArguments(arguments);

            getSupportFragmentManager().beginTransaction()
                    .add(R.id.user_detail_container, fragment)
                    .commit();
        }
        */

    }


    /**
     * ---------------------------------------------------------------------------------------------
     * Set icons, title etc. in action bar
     * ---------------------------------------------------------------------------------------------
     */
    private void setupUpperBar() {
        toolbar = findViewById(R.id.detail_toolbar);
        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            // This ID represents the Home or Up button. In the case of this
            // activity, the Up button is shown. Use NavUtils to allow users
            // to navigate up one level in the application structure. For
            // more details, see the Navigation pattern on Android Design:
            //
            // http://developer.android.com/design/patterns/navigation.html#up-vs-back
            //
            NavUtils.navigateUpTo(this, new Intent(this, SearchUsersActivity.class));
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
