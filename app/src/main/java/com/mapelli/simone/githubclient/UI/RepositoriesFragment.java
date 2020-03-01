package com.mapelli.simone.githubclient.UI;


import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.mapelli.simone.githubclient.R;
import com.mapelli.simone.githubclient.data.entity.UserProfile_Full;
import com.mapelli.simone.githubclient.data.entity.UserRepository;
import com.mapelli.simone.githubclient.util.MyUtil;
import com.mapelli.simone.githubclient.viewmodel.UserRepositoriesViewModel;
import com.mapelli.simone.githubclient.viewmodel.UserRepositoriesViewModelFactory;

import java.util.List;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


public class RepositoriesFragment extends Fragment {

    private static final String TAG = "RepositoriesFragment :";
    private TextView title_list, filter_type;
    private Button filter_btn, asc_order_btn, desc_order_btn;
    private ProgressBar loadingInProgress;
    private TextView emptyListText;
    private Context parentContext;

    private UserDetailActivity parent;
    private UserProfile_Full currentUser;
    private String user_login;

    private List<UserRepository> userRepoList;
    private RecyclerView recyclerView;
    private UserRepoListAdapter adapter;

    private UserRepositoriesViewModel mViewModel;
    private UserRepositoriesViewModelFactory factory;

    private String FILTER_TYPE = "name";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_repositories, container, false);
        parent = (UserDetailActivity) getActivity();
        currentUser = parent.getCurrentUser();
        user_login = currentUser.getLogin();

        parentContext = parent.getBaseContext();

        title_list = rootView.findViewById(R.id.title_list_txt);
        filter_type = rootView.findViewById(R.id.filter_type_txt);

        loadingInProgress = rootView.findViewById(R.id.loading_view);
        emptyListText     = rootView.findViewById(R.id.empty_view);

        setupRecyclerView(rootView);
        setupButton(rootView, parentContext);

        setupRepoListObserver();
        mViewModel.storeUserRepoList_ByName(user_login, "asc");

        return rootView;
    }


    /**
     * ---------------------------------------------------------------------------------------------
     * Set up ViewModel/Livedata for retrieving new users repository data from repo
     */
    private void setupRepoListObserver() {
        // set up viewmodel/livedata to observe data in db and ask for new ones to repo
        factory = new UserRepositoriesViewModelFactory();
        mViewModel = new ViewModelProvider(this, factory).get(UserRepositoriesViewModel.class);

        LiveData<List<UserRepository>> usersList_observed = mViewModel.getUserRepoListObserved();
        usersList_observed.observe(parent, new Observer<List<UserRepository>>() {
            @Override
            public void onChanged(@Nullable List<UserRepository> repoEntries) {
                if (repoEntries != null && !repoEntries.isEmpty()) { // data ready in db
                    userRepoList = repoEntries;
                    updateAdapter(repoEntries);
                    showRepoList();
                }else {                                                         // waiting for data
                    if (MyUtil.isConnectionOk()) {
                        showLoading();
                    } else {
                        showNoInternetConnection();
                    }
                }
            }
        });
    }


    /**
     * --------------------------------------------------------------------------------------------
     * Button creation and setup
     * @param rootView
     * @param parentContext
     */
    private void setupButton(View rootView, Context parentContext) {
        filter_btn = rootView.findViewById(R.id.filter_btn);
        asc_order_btn = rootView.findViewById(R.id.asc_order_btn);
        desc_order_btn = rootView.findViewById(R.id.desc_order_btn);


        filter_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAlertDialog();
            }
        });

        asc_order_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                filter_type.setText(parentContext.getString(R.string.filter_type_start_label) +
                        FILTER_TYPE + " asc ");
                switch (FILTER_TYPE) {
                    case "name":
                        mViewModel.storeUserRepoList_ByName(user_login, "asc");
                        break;
                    case "data creation":
                        mViewModel.storeUserRepoList_ByCreated(user_login, "asc");
                        break;
                    case "data update":
                        mViewModel.storeUserRepoList_ByUpdated(user_login, "asc");
                        break;
                    case "data pushed":
                        mViewModel.storeUserRepoList_ByPushed(user_login, "asc");
                        break;
                    default:
                        break;
                }
            }
        });


        desc_order_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                filter_type.setText(parentContext.getString(R.string.filter_type_start_label) +
                        FILTER_TYPE + " desc ");
                switch (FILTER_TYPE) {
                    case "name":
                        mViewModel.storeUserRepoList_ByName(user_login, "desc");
                        break;
                    case "data creation":
                        mViewModel.storeUserRepoList_ByCreated(user_login, "desc");
                        break;
                    case "data update":
                        mViewModel.storeUserRepoList_ByUpdated(user_login, "desc");
                        break;
                    case "data pushed":
                        mViewModel.storeUserRepoList_ByPushed(user_login, "desc");
                        break;
                    default:
                        break;
                }
            }
        });

    }


    /**
     * --------------------------------------------------------------------------------------------
     * Setup RecyclerView : it starts with currnt user data repos
     */
    private void setupRecyclerView(View rootView) {
        recyclerView = rootView.findViewById(R.id.repo_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(parent.getBaseContext()));

        adapter = new UserRepoListAdapter(parent, userRepoList);
        recyclerView.setAdapter(adapter);

        DividerItemDecoration decoration = new DividerItemDecoration(parent.getBaseContext(),
                LinearLayout.VERTICAL);
        recyclerView.addItemDecoration(decoration);
    }

    /**
     * ---------------------------------------------------------------------------------------------
     * Renew recyclerview data
     * @param userRepoList
     */
    private void updateAdapter(@Nullable List<UserRepository> userRepoList) {
        adapter.setAdapterUserList(userRepoList);
    }


    /**
     * ---------------------------------------------------------------------------------------------
     * Alert dialog for setting filter type
     */
    private void showAlertDialog() {
        AlertDialog alert;
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(parent);
        alertDialog.setTitle(parentContext.getString(R.string.alert_filter_title));

        String[] items = {"name", "data creation", "data update", "data pushed"};
        int checkedItem = 0;
        switch (FILTER_TYPE) {
            case "data creation":
                checkedItem = 1;
                break;
            case "data update":
                checkedItem = 2;
                break;
            case "data pushed":
                checkedItem = 3;
                break;
            default:
                checkedItem = 0;
                break;
        }
        alertDialog.setSingleChoiceItems(items, checkedItem, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which) {
                    case 0:
                        FILTER_TYPE = "name";
                        break;
                    case 1:
                        FILTER_TYPE = "data creation";
                        break;
                    case 2:
                        FILTER_TYPE = "data update";
                        break;
                    case 3:
                        FILTER_TYPE = "data pushed";
                        break;
                }
            }
        });
        alert = alertDialog.create();
        alert.setCanceledOnTouchOutside(true);

        alert.show();

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
    private void showRepoList() {
        loadingInProgress.setVisibility(View.INVISIBLE);
        recyclerView.setVisibility(View.VISIBLE);
        emptyListText.setVisibility(View.INVISIBLE);
    }
}
