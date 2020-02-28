package com.mapelli.simone.githubclient.UI;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;

import androidx.appcompat.widget.Toolbar;

import com.mapelli.simone.githubclient.R;
import com.mapelli.simone.githubclient.Util.AppExecutors;
import com.mapelli.simone.githubclient.data.dummy.DummyContent;
import com.mapelli.simone.githubclient.data.entity.UserProfile_Mini;
import com.mapelli.simone.githubclient.network.NetworkRequests;

import java.util.ArrayList;


public class SearchUsersActivity extends AppCompatActivity {

    AppExecutors appExecutors;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_list);

        setupActionBar();

        View recyclerView = findViewById(R.id.item_list);
        assert recyclerView != null;
        setupRecyclerView((RecyclerView) recyclerView);


    }


    /**
     * ---------------------------------------------------------------------------------------------
     * Set icons, title etc. in action bar
     * ---------------------------------------------------------------------------------------------
     */
    private void setupActionBar() {
        Toolbar toolbar =  findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle(getString(R.string.search_title));
    }


    private void setupRecyclerView(@NonNull RecyclerView recyclerView) {
        recyclerView.setAdapter(new UsersListAdapter(this, DummyContent.ITEMS));
    }



    /**
     * ---------------------------------------------------------------------------------------------
     * Menu stuff
     * ---------------------------------------------------------------------------------------------
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.options_menu, menu);

        return true;
    }





}
