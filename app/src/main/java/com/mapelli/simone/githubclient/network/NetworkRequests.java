package com.mapelli.simone.githubclient.network;


import android.util.Log;

import com.mapelli.simone.githubclient.data.entity.UserProfile_Full;
import com.mapelli.simone.githubclient.data.entity.UserProfile_Mini;
import com.mapelli.simone.githubclient.data.entity.UserProfile_Mini_List;
import com.mapelli.simone.githubclient.data.entity.UserRepository;

import java.util.ArrayList;
import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class NetworkRequests {
    private final static String TAG  =  NetworkRequests.class.getSimpleName();
    public final static String BASE_URL_API  = "https://api.github.com";


    private MutableLiveData<List<UserProfile_Mini>> usersProfiles_Minis = new MutableLiveData<>();
    private MutableLiveData<UserProfile_Full> userProfile_Full          = new MutableLiveData<>();
    private MutableLiveData<List<UserRepository>> userRepositories      = new MutableLiveData<>();

    public LiveData<List<UserProfile_Mini>> getUsersProfilesMini()  { return usersProfiles_Minis; }
    public LiveData<UserProfile_Full>       getUserProfilesFull()   { return userProfile_Full; }
    public LiveData<List<UserRepository>>   getUserRepositories()   { return userRepositories; }


    /**
     * ---------------------------------------------------------------------------------------------
     * Recover user list based on keyword.
     * @param keyword
     */
    /*
    public void doUsersSearch(String keyword){
        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl(BASE_URL_API)
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
                for(UserProfile_Mini profile: userList) {
                    Log.d(TAG, "onResponse: login + " + profile.getLogin() +
                            " id : " + profile.getId() +
                            " avatar_url : " + profile.getAvatar_url() +
                            " login : " + profile.getLogin()
                    );
                }

                usersProfiles_Minis.postValue(userList);
            }

            @Override
            public void onFailure(Call<UserProfile_Mini_List> call, Throwable t) {
                Log.e(TAG, "onFailure: getUserIdSearch ",  t);
            }
        });
    }

     */



    /**
     * ---------------------------------------------------------------------------------------------
     * Recover user list based on keyword
     * NB : the list of user profiles recovered with api :
     * https://api.github.com/search/users?q=<search keywords>  with seems whatever parameters
     * (...but I'm still investigating) do not include the full name (field "name") with which
     * the search are done (#issue13).
     * So another call getUserProfileFullByLogin using "login" as parameter is necessary to get "name
     * after selecting a user in list ", for user details
     * @param keyword
     */
    public void doUsersSearch(String keyword, String page_num, String per_page){
        Log.d(TAG, "doUsersSearch: CALLED");
        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl(BASE_URL_API)
                .addConverterFactory(GsonConverterFactory.create());

        Retrofit retrofit = builder.build();
        NetworkService client = retrofit.create(NetworkService.class);

        Call<UserProfile_Mini_List> call = client.usersListSearch_Paging(keyword,page_num, per_page);
        call.enqueue(new Callback<UserProfile_Mini_List>() {
            @Override
            public void onResponse(Call<UserProfile_Mini_List> call,
                                   Response<UserProfile_Mini_List> response) {
                UserProfile_Mini_List result = response.body();
                ArrayList<UserProfile_Mini> userList = result.getUserList();
                for(UserProfile_Mini profile: userList) {
                    Log.d(TAG, "onResponse: login + " + profile.getLogin() +
                            " id : " + profile.getId() +
                            " avatar_url : " + profile.getAvatar_url() +
                            " login : " + profile.getLogin()
                    );
                }

                usersProfiles_Minis.postValue(userList);
            }

            @Override
            public void onFailure(Call<UserProfile_Mini_List> call, Throwable t) {
                Log.e(TAG, "onFailure: getUserIdSearch ",  t);
            }
        });
    }




    /**
     * ---------------------------------------------------------------------------------------------
     * Get single user full profile by login
     * @param login
     */
    public void getUserProfileFullByLogin(String login){
        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl(BASE_URL_API)
                .addConverterFactory(GsonConverterFactory.create());

        Retrofit retrofit = builder.build();
        NetworkService client = retrofit.create(NetworkService.class);

        Call<UserProfile_Full> call = client.userProfileByUserLogin(login);
        call.enqueue(new Callback<UserProfile_Full>() {
            @Override
            public void onResponse(Call<UserProfile_Full> call,
                                   Response<UserProfile_Full> response) {
                UserProfile_Full profile = response.body();
                Log.d(TAG, "onResponse: login + "+profile.getLogin() +
                        " id : "+profile.getId()+
                        " avatar_url : "+profile.getAvatar_url()+
                        " email : "+profile.getEmail()+
                        " location : "+profile.getLocation()+
                        " name : "+profile.getName()+
                        " repos_url : "+profile.getRepos_url()
                );

                userProfile_Full.postValue(profile);
            }

            @Override
            public void onFailure(Call<UserProfile_Full> call, Throwable t) {
                Log.e(TAG, "onFailure: getUserProfileFullById ",  t);
            }
        });
    }




    /**
     * ---------------------------------------------------------------------------------------------
     * Repo with name in asc/desc direction
     * @param login
     */
    public void getRepoFilterByName_Direction(String login, String direction){
        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl(BASE_URL_API)
                .addConverterFactory(GsonConverterFactory.create());

        Retrofit retrofit = builder.build();
        NetworkService client = retrofit.create(NetworkService.class);

        Call<ArrayList<UserRepository>> call = client.userReposBy_name_direction(login, direction);

        call.enqueue(new Callback<ArrayList<UserRepository>>() {
            @Override
            public void onResponse(Call<ArrayList<UserRepository>> call,
                                   Response<ArrayList<UserRepository>> response) {
                ArrayList<UserRepository> listRepo = response.body();
                for(UserRepository repo:listRepo){
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
                            "forks_count + "+repo.getForks_count() );
                }
                userRepositories.postValue(listRepo);
            }

            @Override
            public void onFailure(Call<ArrayList<UserRepository>> call, Throwable t) {
                Log.e(TAG, "onFailure: getUserIdSearch ",  t);
            }
        });
    }



    /**
     * ---------------------------------------------------------------------------------------------
     * Repo with created in asc/desc direction
     * @param login
     */
    public void getRepoFilterByCreated_Direction(String login, String direction){
        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl(BASE_URL_API)
                .addConverterFactory(GsonConverterFactory.create());

        Retrofit retrofit = builder.build();
        NetworkService client = retrofit.create(NetworkService.class);

        Call<ArrayList<UserRepository>> call =
                client.userReposBy_created_direction(login,"created", direction);
        call.enqueue(new Callback<ArrayList<UserRepository>>() {
            @Override
            public void onResponse(Call<ArrayList<UserRepository>> call,
                                   Response<ArrayList<UserRepository>> response) {
                ArrayList<UserRepository> listRepo = response.body();
                for(UserRepository repo:listRepo){
                    Log.d(TAG, "onResponse:  created_at : "+repo.getCreated_at());
                }
                userRepositories.postValue(listRepo);

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
                .baseUrl(BASE_URL_API)
                .addConverterFactory(GsonConverterFactory.create());

        Retrofit retrofit = builder.build();
        NetworkService client = retrofit.create(NetworkService.class);

        Call<ArrayList<UserRepository>> call =
                client.reposForuser_updated_asc(login,"updated", direction);
        call.enqueue(new Callback<ArrayList<UserRepository>>() {
            @Override
            public void onResponse(Call<ArrayList<UserRepository>> call,
                                   Response<ArrayList<UserRepository>> response) {
                ArrayList<UserRepository> listRepo = response.body();
                for(UserRepository repo:listRepo){
                    Log.d(TAG, "onResponse:  updated_at : "+repo.getUpdated_at());
                }
                userRepositories.postValue(listRepo);
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
                .baseUrl(BASE_URL_API)
                .addConverterFactory(GsonConverterFactory.create());

        Retrofit retrofit = builder.build();
        NetworkService client = retrofit.create(NetworkService.class);

        Call<ArrayList<UserRepository>> call = client.reposForuser_pushed_asc(login,"pushed", direction);
        call.enqueue(new Callback<ArrayList<UserRepository>>() {
            @Override
            public void onResponse(Call<ArrayList<UserRepository>> call,
                                   Response<ArrayList<UserRepository>> response) {
                ArrayList<UserRepository> listRepo = response.body();
                for(UserRepository repo:listRepo){
                    Log.d(TAG, "onResponse:  pushed_at : "+repo.getPushed_at());
                }
                userRepositories.postValue(listRepo);
            }

            @Override
            public void onFailure(Call<ArrayList<UserRepository>> call, Throwable t) {
                Log.e(TAG, "onFailure: getUserIdSearch ",  t);
            }
        });
    }





}
