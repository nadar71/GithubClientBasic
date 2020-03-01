package com.mapelli.simone.githubclient.UI;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import com.google.android.material.tabs.TabItem;
import com.google.android.material.tabs.TabLayout;
import com.mapelli.simone.githubclient.R;
import com.mapelli.simone.githubclient.data.entity.UserProfile_Full;
import com.mapelli.simone.githubclient.viewmodel.UserDetailViewModel;
import com.mapelli.simone.githubclient.viewmodel.UserDetailViewModelFactory;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.NavUtils;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager.widget.ViewPager;


public class UserDetailActivity extends AppCompatActivity {

    private static final String TAG = UserDetailActivity.class.getSimpleName();

    private Toolbar toolbar;

    private TabLayout tabLayout;
    private ViewPager viewPager;
    private DetailSectionAdapter detailSectionAdapter;
    private TabItem tabProfile;
    private TabItem tabRepositories;

    // current user data, toolbar title
    public static final String ARG_ITEM_ID = "item_id";
    private String user_login;
    private UserProfile_Full currentUser;

    private UserDetailViewModel mViewModel;
    private UserDetailViewModelFactory factory;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_detail);

        tabLayout = findViewById(R.id.tablayout);
        tabProfile = findViewById(R.id.tabProfile);
        tabRepositories = findViewById(R.id.tabRepositories);
        viewPager = findViewById(R.id.viewPager);

        Intent intent = getIntent();
        user_login = intent.getStringExtra(ARG_ITEM_ID);

        setupUpperBar();

        setupUserProfileObserver();
        mViewModel.storeUserFull(user_login);


    }

    /**
     * ---------------------------------------------------------------------------------------------
     * Set up ViewModel/Livedata for retrieving new users profile data from repo
     */
    private void setupUserProfileObserver() {
        factory = new UserDetailViewModelFactory();
        mViewModel = new ViewModelProvider(this, factory).get(UserDetailViewModel.class);

        LiveData<UserProfile_Full> currentUser_observed = mViewModel.getUserObserved();
        currentUser_observed.observe(this, new Observer<UserProfile_Full>() {
            @Override
            public void onChanged(@Nullable UserProfile_Full userEntry) {
                if (userEntry != null) {
                    currentUser = userEntry;
                    setupViewPager();
                }
            }
        });
    }


    /**
     * ---------------------------------------------------------------------------------------------
     * Used to export the current user to child fragments
     */
    public UserProfile_Full getCurrentUser() {
        return currentUser;
    }


    /**
     * ---------------------------------------------------------------------------------------------
     * Set icons, title etc. in action bar
     */
    private void setupUpperBar() {
        toolbar = findViewById(R.id.detail_toolbar);

        if (user_login != "") {
            Log.d(TAG, "currentuser login : " + user_login);
            toolbar.setTitle(user_login);
        }

        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }


    }


    /**
     * ---------------------------------------------------------------------------------------------
     * Setup adapter and ViewPager
     */
    private void setupViewPager() {
        detailSectionAdapter = new DetailSectionAdapter(getSupportFragmentManager(),
                tabLayout.getTabCount());

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
    }


    /**
     * ---------------------------------------------------------------------------------------------
     * Home button setup
     * @param item
     * @return
     */
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
