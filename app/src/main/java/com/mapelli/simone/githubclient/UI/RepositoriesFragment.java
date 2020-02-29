package com.mapelli.simone.githubclient.UI;


import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mapelli.simone.githubclient.R;
import com.mapelli.simone.githubclient.data.entity.UserProfile_Full;
import com.mapelli.simone.githubclient.data.entity.UserRepository;
import com.mapelli.simone.githubclient.network.NetworkService;

import java.util.ArrayList;
import java.util.List;


public class RepositoriesFragment extends Fragment {

    private static final String TAG = "RepositoriesFragment :";
    private TextView title_list, filter_type;
    private Button   filter_btn,asc_order_btn,desc_order_btn;
    private Context parentContext;

    private UserDetailActivity parent;
    private UserProfile_Full currentUser;
    private String user_login;

    private ArrayList<UserRepository> userRepoList;
    private RecyclerView recyclerView;
    private UserRepoListAdapter adapter;

    private String FILTER_TYPE = "name";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView =  inflater.inflate(R.layout.fragment_repositories, container, false);
        parent = (UserDetailActivity) getActivity();
        currentUser = parent.getCurrentUser();
        user_login  = currentUser.getLogin();

        parentContext = parent.getBaseContext();

        title_list     = rootView.findViewById(R.id.title_list_txt);
        filter_type    = rootView.findViewById(R.id.filter_type_txt);

        setupRecyclerView(rootView);
        setupButton(rootView, parentContext);


        // **** TODO : FOR DEBUG ONLY
        getRepoFilterByName_Direction(user_login,"asc");

        return rootView;
    }




    /**
     * --------------------------------------------------------------------------------------------
     * Button creation and setup
     * @param rootView
     * @param parentContext
     */
    private void setupButton(View rootView, Context parentContext) {
        filter_btn     = rootView.findViewById(R.id.filter_btn);
        asc_order_btn  = rootView.findViewById(R.id.asc_order_btn);
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
                //**** TODO : FOR DEBUG ONLY
                switch(FILTER_TYPE){
                    case "name"          : getRepoFilterByName_Direction(user_login,"asc");    break;
                    case "data creation" : getRepoFilterByCreated_Direction(user_login,"asc"); break;
                    case "data update"   : getRepoFilterByUpdated_Direction(user_login,"asc"); break;
                    case "data pushed"   : getRepoFilterByPushed_Direction(user_login,"asc");  break;
                    default              : break;
                }
            }
        });


        desc_order_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                filter_type.setText(parentContext.getString(R.string.filter_type_start_label) +
                        FILTER_TYPE + " desc ");
                //**** TODO : FOR DEBUG ONLY
                switch(FILTER_TYPE){
                    case "name"          : getRepoFilterByName_Direction(user_login,"desc");    break;
                    case "data creation" : getRepoFilterByCreated_Direction(user_login,"desc"); break;
                    case "data update"   : getRepoFilterByUpdated_Direction(user_login,"desc"); break;
                    case "data pushed"   : getRepoFilterByPushed_Direction(user_login,"desc");  break;
                    default              : break;
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
        alertDialog.setTitle(parentContext.getString(R.string.alert_filter_title ));

        String[] items = {"name","data creation","data update","data pushed"};
        int checkedItem = 0;
        switch(FILTER_TYPE){
            case "data creation" : checkedItem = 1; break;
            case "data update"   : checkedItem = 2; break;
            case "data pushed"   : checkedItem = 3; break;
            default              : checkedItem = 0; break;
        }
        alertDialog.setSingleChoiceItems(items, checkedItem, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which) {
                    case 0: FILTER_TYPE = "name"; break;
                    case 1: FILTER_TYPE = "data creation"; break;
                    case 2: FILTER_TYPE = "data update"; break;
                    case 3: FILTER_TYPE = "data pushed"; break;
                }
            }
        });


        alert = alertDialog.create();
        /*
        alertDialog.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                alert.cancel();
            }
        });
        */
        alert.setCanceledOnTouchOutside(true);

        alert.show();

    }


    //==============================================================================================
    //                                     DEBUG
    // **** ONLY FOR DEBUGGING, DELETED AFTER CREATING REPOSITORY + VIEWMODEL/LIVEDATA LAYER

    public void getRepoFilterByName_Direction(String user, String direction){
        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl("https://api.github.com")
                .addConverterFactory(GsonConverterFactory.create());

        Retrofit retrofit = builder.build();
        NetworkService client = retrofit.create(NetworkService.class);

        Call<ArrayList<UserRepository>> call = client.userReposBy_name_direction(user, direction);
        call.enqueue(new Callback<ArrayList<UserRepository>>() {
            @Override
            public void onResponse(Call<ArrayList<UserRepository>> call,
                                   Response<ArrayList<UserRepository>> response) {
                userRepoList = response.body();
                updateAdapter(userRepoList);

                for(UserRepository repo:userRepoList){
                    repo.setUser_id_owner(user);
                    Log.d(TAG, "onResponse: " +
                            "user_id_owner + "+repo.getUser_id_owner()+
                            "name + "+repo.getName() +
                            "full_name + "+repo.getFull_name() +
                            "html_url + "+repo.getHtml_url() +
                            "created_at + "+repo.getCreated_at() +
                            "updated_at + "+repo.getUpdated_at() +
                            "pushed_at + "+repo.getPushed_at() +
                            "stargazers_count + "+repo.getStargazers_count() +
                            "forks_count + "+repo.getForks_count()
                    );
                }
            }

            @Override
            public void onFailure(Call<ArrayList<UserRepository>> call, Throwable t) {
                Log.e(TAG, "onFailure: getUserProfileFullById ",  t);
            }
        });

    }

    public void getRepoFilterByCreated_Direction(String login, String direction){
        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl("https://api.github.com")
                .addConverterFactory(GsonConverterFactory.create());

        Retrofit retrofit = builder.build();
        NetworkService client = retrofit.create(NetworkService.class);

        Call<ArrayList<UserRepository>> call =
                client.userReposBy_created_direction(login,"created", direction);
        call.enqueue(new Callback<ArrayList<UserRepository>>() {
            @Override
            public void onResponse(Call<ArrayList<UserRepository>> call,
                                   Response<ArrayList<UserRepository>> response) {
                userRepoList = response.body();
                updateAdapter(userRepoList);
                for(UserRepository repo:userRepoList){
                    Log.d(TAG, "onResponse:  created_at : "+repo.getCreated_at());
                }
            }

            @Override
            public void onFailure(Call<ArrayList<UserRepository>> call, Throwable t) {
                Log.e(TAG, "onFailure: getUserIdSearch ",  t);
            }
        });
    }

    /**
     * ---------------------------------------------------------------------------------------------
     * Repo with updated in asc/desc direction
     * @param login
     */
    public void getRepoFilterByUpdated_Direction(String login, String direction){
        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl("https://api.github.com")
                .addConverterFactory(GsonConverterFactory.create());

        Retrofit retrofit = builder.build();
        NetworkService client = retrofit.create(NetworkService.class);

        Call<ArrayList<UserRepository>> call =
                client.reposForuser_updated_asc(login,"updated", direction);
        call.enqueue(new Callback<ArrayList<UserRepository>>() {
            @Override
            public void onResponse(Call<ArrayList<UserRepository>> call,
                                   Response<ArrayList<UserRepository>> response) {
                userRepoList = response.body();
                updateAdapter(userRepoList);
                for(UserRepository repo:userRepoList){
                    Log.d(TAG, "onResponse:  updated_at : "+repo.getUpdated_at());
                }
            }

            @Override
            public void onFailure(Call<ArrayList<UserRepository>> call, Throwable t) {
                Log.e(TAG, "onFailure: getUserIdSearch ",  t);
            }
        });
    }




    /**
     * ---------------------------------------------------------------------------------------------
     * Repo with pushed in asc/desc direction
     * @param login
     */
    public void getRepoFilterByPushed_Direction(String login, String direction){
        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl("https://api.github.com")
                .addConverterFactory(GsonConverterFactory.create());

        Retrofit retrofit = builder.build();
        NetworkService client = retrofit.create(NetworkService.class);

        Call<ArrayList<UserRepository>> call = client.reposForuser_pushed_asc(login,"pushed", direction);
        call.enqueue(new Callback<ArrayList<UserRepository>>() {
            @Override
            public void onResponse(Call<ArrayList<UserRepository>> call,
                                   Response<ArrayList<UserRepository>> response) {
                userRepoList = response.body();
                updateAdapter(userRepoList);
                for(UserRepository repo:userRepoList){
                    Log.d(TAG, "onResponse:  pushed_at : "+repo.getPushed_at());
                }
            }

            @Override
            public void onFailure(Call<ArrayList<UserRepository>> call, Throwable t) {
                Log.e(TAG, "onFailure: getUserIdSearch ",  t);
            }
        });
    }

}
