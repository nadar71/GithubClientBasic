package com.mapelli.simone.githubclient.UI;


import android.os.Bundle;

import androidx.annotation.NonNull;
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
import com.mapelli.simone.githubclient.data.entity.UserProfile_Mini;
import com.mapelli.simone.githubclient.data.entity.UserRepository;
import com.mapelli.simone.githubclient.network.NetworkRequests;
import com.mapelli.simone.githubclient.network.NetworkService;

import java.util.ArrayList;
import java.util.List;


public class RepositoriesFragment extends Fragment {

    private static final String TAG = "RepositoriesFragment :";
    private TextView title_list, filter_type;
    private Button   filter_btn,asc_order_btn,desc_order_btn;

    private UserDetailActivity parent;
    private UserProfile_Full currentUser;

    private ArrayList<UserRepository> userRepoList;
    private RecyclerView recyclerView;
    private UserRepoListAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView =  inflater.inflate(R.layout.fragment_repositories, container, false);
        parent = (UserDetailActivity) getActivity();
        currentUser = parent.getCurrentUser();

        title_list     = rootView.findViewById(R.id.title_list_txt);
        filter_type    = rootView.findViewById(R.id.filter_type_txt);
        filter_btn     = rootView.findViewById(R.id.filter_btn);
        asc_order_btn  = rootView.findViewById(R.id.asc_order_btn);
        desc_order_btn = rootView.findViewById(R.id.desc_order_btn);

        setupRecyclerView(rootView);

        // **** TODO : FOR DEBUG ONLY
        getUserRepo(currentUser.getLogin());

        return rootView;
    }


    /** --------------------------------------------------------------------------------------------
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



    //==============================================================================================
    //                                     DEBUG
    // **** ONLY FOR DEBUGGING, DELETED AFTER CREATING REPOSITORY + VIEWMODEL/LIVEDATA LAYER

    public void getUserRepo(String login){
        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl("https://api.github.com")
                .addConverterFactory(GsonConverterFactory.create());

        Retrofit retrofit = builder.build();
        NetworkService client = retrofit.create(NetworkService.class);

        Call<ArrayList<UserRepository>> call = client.reposForuser(login);
        call.enqueue(new Callback<ArrayList<UserRepository>>() {
            @Override
            public void onResponse(Call<ArrayList<UserRepository>> call,
                                   Response<ArrayList<UserRepository>> response) {
                userRepoList = response.body();
                updateAdapter(userRepoList);

                for(UserRepository repo:userRepoList){
                    repo.setUser_id_owner(login);
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


}
