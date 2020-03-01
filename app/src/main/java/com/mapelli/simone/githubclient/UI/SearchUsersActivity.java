package com.mapelli.simone.githubclient.UI;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.SearchView;
import android.widget.TextView;

import com.mapelli.simone.githubclient.R;
import com.mapelli.simone.githubclient.data.entity.UserProfile_Mini;
import com.mapelli.simone.githubclient.util.MyUtil;
import com.mapelli.simone.githubclient.viewmodel.SearchUserViewModelFactory;
import com.mapelli.simone.githubclient.viewmodel.UserSearchViewModel;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


public class SearchUsersActivity extends AppCompatActivity {
    private final static String TAG = SearchUsersActivity.class.getSimpleName();

    private List<UserProfile_Mini> userList = new ArrayList<>();
    private RecyclerView recyclerView;
    private ProgressBar loadingInProgress;
    private TextView emptyListText;
    private UsersListAdapter adapter;

    private UserSearchViewModel mViewModel;
    private SearchUserViewModelFactory factory;

    private Button loadMore_btn;
    private int pageCount = 1;
    private int userPerPage = 30;
    private String currentSearchingKeywords = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_list);
        setupGenericViews();
        setupActionBar();
        setupRecyclerView();
        setupUsersDataObserver();
        showUserList();
    }


    /**
     * ---------------------------------------------------------------------------------------------
     * Set up progress bar,btn etc.
     */
    private void setupGenericViews() {
        loadingInProgress = findViewById(R.id.loading_view);
        emptyListText     = findViewById(R.id.empty_view);


        loadMore_btn = findViewById(R.id.loadMore_btn);
        loadMore_btn.setVisibility(View.INVISIBLE);

        loadMore_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mViewModel.storeUserList(currentSearchingKeywords,
                        Integer.toString(pageCount),
                        Integer.toString(userPerPage));
                pageCount++;
            }
        });

    }


    /**
     * ---------------------------------------------------------------------------------------------
     * Set up ViewModel/Livedata for retrieving new users nick list from repo
     */
    private void setupUsersDataObserver() {
        factory = new SearchUserViewModelFactory();
        mViewModel = new ViewModelProvider(this, factory).get(UserSearchViewModel.class);

        LiveData<List<UserProfile_Mini>> usersList_observed = mViewModel.getUserListObserved();
        usersList_observed.observe(this, new Observer<List<UserProfile_Mini>>() {
            @Override
            public void onChanged(@Nullable List<UserProfile_Mini> userEntries) {
                if (userEntries != null && !userEntries.isEmpty()) { // data ready in db
                    userList = userEntries;
                    int currentPos = adapter.getItemCount();
                    updateAdapter(userEntries);

                    showUserList();
                    bumpUpList(currentPos);
                }else {
                    if (MyUtil.isConnectionOk() && !currentSearchingKeywords.isEmpty()) {
                        showLoading();
                    } else if(!MyUtil.isConnectionOk()){
                        showNoInternetConnection();
                    }
                }
            }
        });
    }


    /**
     * ---------------------------------------------------------------------------------------------
     * Set icons, title etc. in action bar
     */
    private void setupActionBar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle(getString(R.string.search_title));
    }


    /**
     * --------------------------------------------------------------------------------------------
     * Setup RecyclerView : it starts empty.
     */
    private void setupRecyclerView() {
        recyclerView = findViewById(R.id.userList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        adapter = new UsersListAdapter(this, userList);
        recyclerView.setAdapter(adapter);

        DividerItemDecoration decoration = new DividerItemDecoration(getApplicationContext(),
                LinearLayout.VERTICAL);
        recyclerView.addItemDecoration(decoration);
    }


    /**
     * --------------------------------------------------------------------------------------------
     * Scrollup a bit RecyclerView : useful after new loading by pagination
     */
    private void bumpUpList(int currentPos) {
        recyclerView.post(new Runnable() {
            @Override
            public void run() {
                recyclerView.smoothScrollToPosition(adapter.getItemCount() - (userPerPage - 2));
            }
        });
    }

    /**
     * ---------------------------------------------------------------------------------------------
     * Renew recyclerview data
     * @param userList
     */
    private void updateAdapter(@Nullable List<UserProfile_Mini> userList) {
        if (!userList.isEmpty() && !currentSearchingKeywords.isEmpty()) {
            loadMore_btn.setVisibility(View.VISIBLE);
        }
        adapter.setAdapterUserList(userList);
    }



    /**
     * ---------------------------------------------------------------------------------------------
     * Show No Internet Connection view
     */
    private void showNoInternetConnection() {
        loadingInProgress.setVisibility(View.GONE);
        recyclerView.setVisibility(View.GONE);
        emptyListText.setVisibility(View.VISIBLE);
        emptyListText.setText(R.string.no_connection);
    }

    /**
     * ---------------------------------------------------------------------------------------------
     * Show loading in progress view, hiding  list
     */
    private void showLoading() {
        loadingInProgress.setVisibility(View.VISIBLE);
        recyclerView.setVisibility(View.GONE);
        emptyListText.setVisibility(View.VISIBLE);
        emptyListText.setText(R.string.searching);
    }

    /**
     * ---------------------------------------------------------------------------------------------
     * Show list after loading/retrieving data completed
     */
    private void showUserList() {
        loadingInProgress.setVisibility(View.INVISIBLE);
        recyclerView.setVisibility(View.VISIBLE);
        emptyListText.setVisibility(View.INVISIBLE);
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
                showLoading();

                currentSearchingKeywords = keywords;
                mViewModel.deleteUserList(); // delete previous results
                mViewModel.storeUserList(currentSearchingKeywords,
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





}
