package com.mapelli.simone.githubclient.UI;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.LinearLayout;
import android.widget.SearchView;

import com.mapelli.simone.githubclient.R;
import com.mapelli.simone.githubclient.Util.AppExecutors;
import com.mapelli.simone.githubclient.data.entity.UserProfile_Mini;
import com.mapelli.simone.githubclient.data.entity.UserProfile_Mini_List;
import com.mapelli.simone.githubclient.network.NetworkService;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.RecyclerView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class SearchUsersActivity extends AppCompatActivity {
    private final static String TAG  = SearchUsersActivity.class.getSimpleName();

    AppExecutors appExecutors;
    ArrayList<UserProfile_Mini> userList;
    RecyclerView recyclerView;
    private UsersListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_list);

        setupActionBar();
        recyclerView = findViewById(R.id.userList);

        setupRecyclerView(recyclerView);

    }


    /**
     * ---------------------------------------------------------------------------------------------
     * Set icons, title etc. in action bar
     */
    private void setupActionBar() {
        Toolbar toolbar =  findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle(getString(R.string.search_title));
    }


    /** --------------------------------------------------------------------------------------------
     * Setup RecyclerView : it starts empty
     * @param recyclerView
     */
    private void setupRecyclerView(@NonNull RecyclerView recyclerView) {
        adapter = new UsersListAdapter(this,userList);

        // recyclerView.setAdapter(new UsersListAdapter(this,DummyContent.ITEMS));
        recyclerView.setAdapter(adapter);

        DividerItemDecoration decoration = new DividerItemDecoration(getApplicationContext(),
                LinearLayout.VERTICAL);
        recyclerView.addItemDecoration(decoration);
    }

    /**
     * ---------------------------------------------------------------------------------------------
     * Renew recyclerview data
     * @param userList
     */
    private void updateAdapter(@Nullable List<UserProfile_Mini> userList) {
        adapter.setAdapterUserList(userList);
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

        MenuItem search_item = menu.findItem(R.id.search);

        SearchView searchView = (SearchView) search_item.getActionView();
        searchView.setFocusable(false);
        searchView.setQueryHint("Search user");
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {


            @Override
            public boolean onQueryTextSubmit(String keywords) {
                // **** FOR DEBUG ONLY
                retrievedata(keywords);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                return false;
            }
        });

        return true;
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        return super.onOptionsItemSelected(item);
    }



    //==============================================================================================
    //                                     DEBUG
    // **** ONLY FOR DEBUGGING, DELETED AFTER CREATING REPOSITORY + VIEWMODEL/LIVEDATA LAYER
    public void retrievedata(String keyword) {
        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl("https://api.github.com")
                .addConverterFactory(GsonConverterFactory.create());

        Retrofit retrofit = builder.build();
        NetworkService client = retrofit.create(NetworkService.class);

        Call<UserProfile_Mini_List> call = client.usersListSearch(keyword);
        call.enqueue(new Callback<UserProfile_Mini_List>() {
            @Override
            public void onResponse(Call<UserProfile_Mini_List> call,
                                   Response<UserProfile_Mini_List> response) {
                UserProfile_Mini_List result = response.body();
                ArrayList<UserProfile_Mini> userList = result.getUserList();
                updateAdapter(userList);
            }

            @Override
            public void onFailure(Call<UserProfile_Mini_List> call, Throwable t) {
                Log.e(TAG, "onFailure: getUserIdSearch ", t);
            }
        });
    }





}
