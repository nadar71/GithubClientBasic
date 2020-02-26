package com.mapelli.simone.githubclient;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.widget.Toolbar;

import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.ActionBar;
import androidx.core.app.NavUtils;

import android.view.MenuItem;


public class UserDetailActivity extends AppCompatActivity {
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_detail);


        setupUpperBar();


        if (savedInstanceState == null) {
            Bundle arguments = new Bundle();
            arguments.putString(UserDetailFragment.ARG_ITEM_ID,
                    getIntent().getStringExtra(UserDetailFragment.ARG_ITEM_ID));

            UserDetailFragment fragment = new UserDetailFragment();
            fragment.setArguments(arguments);

            getSupportFragmentManager().beginTransaction()
                    .add(R.id.item_detail_container, fragment)
                    .commit();
        }
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


    // Used by UserDetailFragment
    public void setupToolbarTitle(String title) {
        toolbar.setTitle(title);
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
