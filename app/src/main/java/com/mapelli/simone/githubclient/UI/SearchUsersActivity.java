package com.mapelli.simone.githubclient.UI;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.SearchView;

import com.mapelli.simone.githubclient.R;
import com.mapelli.simone.githubclient.data.entity.UserProfile_Mini;
import com.mapelli.simone.githubclient.data.entity.UserProfile_Mini_List;
import com.mapelli.simone.githubclient.network.NetworkService;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class SearchUsersActivity extends AppCompatActivity {
    private final static String TAG  = SearchUsersActivity.class.getSimpleName();

    private ArrayList<UserProfile_Mini> userList = new ArrayList<>();
    private RecyclerView recyclerView;
    private UsersListAdapter adapter;
    private Button loadMore_btn;
    private int pageCount   = 1;
    private int userPerPage = 30;
    private String currentSearchingKeywords = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_list);

        loadMore_btn = findViewById(R.id.loadMore_btn);
        loadMore_btn.setVisibility(View.INVISIBLE);
        loadMore_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // **** TODO : FOR DEBUG ONLY
                usersListSearch_Paging(currentSearchingKeywords,
                        Integer.toString(pageCount),
                        Integer.toString(userPerPage));
                pageCount++;


            }
        });

        setupActionBar();
        setupRecyclerView();
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
     * Setup RecyclerView : it starts empty. TODO : with data layer, load the last search results
     */
    private void setupRecyclerView() {
        recyclerView = findViewById(R.id.userList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        adapter = new UsersListAdapter(this,userList);
        recyclerView.setAdapter(adapter);

        DividerItemDecoration decoration = new DividerItemDecoration(getApplicationContext(),
                LinearLayout.VERTICAL);
        recyclerView.addItemDecoration(decoration);
    }


    /** --------------------------------------------------------------------------------------------
     * Scrollup a bit RecyclerView : useful after new loading by pagination
     */
    private void bumpUpList(int currentPos) {
        recyclerView.post(new Runnable() {
            @Override
            public void run() {
                recyclerView.smoothScrollToPosition(adapter.getItemCount()-(userPerPage-2));
            }
        });
    }

    /**
     * ---------------------------------------------------------------------------------------------
     * Renew recyclerview data
     * @param userList
     */
    private void updateAdapter(@Nullable List<UserProfile_Mini> userList) {
        if(!userList.isEmpty()){
            loadMore_btn.setVisibility(View.VISIBLE);
        }
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
                currentSearchingKeywords = keywords;
                // **** TODO : FOR DEBUG ONLY
                // retrievedata(keywords);
                usersListSearch_Paging(currentSearchingKeywords,
                        Integer.toString(pageCount),
                        Integer.toString(userPerPage));

                pageCount++;

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
                userList = result.getUserList();
                updateAdapter(userList);
            }

            @Override
            public void onFailure(Call<UserProfile_Mini_List> call, Throwable t) {
                Log.e(TAG, "onFailure: getUserIdSearch ", t);
            }
        });
    }


    /**
     * ---------------------------------------------------------------------------------------------
     * Recover user list based on keyword.
     * @param keyword
     */
    public void usersListSearch_Paging(String keyword, String page_num, String per_page){
        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl("https://api.github.com")
                .addConverterFactory(GsonConverterFactory.create());

        Retrofit retrofit = builder.build();
        NetworkService client = retrofit.create(NetworkService.class);

        Call<UserProfile_Mini_List> call = client.usersListSearch_Paging(keyword,page_num, per_page);
        call.enqueue(new Callback<UserProfile_Mini_List>() {
            @Override
            public void onResponse(Call<UserProfile_Mini_List> call,
                                   Response<UserProfile_Mini_List> response) {
                UserProfile_Mini_List result = response.body();
                ArrayList<UserProfile_Mini> userList_newPage = result.getUserList();
                for(UserProfile_Mini profile: userList_newPage) {
                    // update userList
                    userList.add(profile);

                    Log.d(TAG, "onResponse: login + " + profile.getLogin() +
                            " id : " + profile.getId() +
                            " avatar_url : " + profile.getAvatar_url() +
                            " login : " + profile.getLogin()
                    );
                }
                int currentPos = adapter.getItemCount();
                updateAdapter(userList);
                bumpUpList(currentPos);



            }

            @Override
            public void onFailure(Call<UserProfile_Mini_List> call, Throwable t) {
                Log.e(TAG, "onFailure: getUserIdSearch ",  t);
            }
        });
    }




}
