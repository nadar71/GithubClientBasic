package com.mapelli.simone.githubclient.network;


import android.util.Log;

import com.mapelli.simone.githubclient.data.entity.UserProfile_Full;
import com.mapelli.simone.githubclient.data.entity.UserProfile_Mini;
import com.mapelli.simone.githubclient.data.entity.UserProfile_Mini_List;
import com.mapelli.simone.githubclient.data.entity.UserRepository;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class NetworkRequests {

    private final static String TAG  =  NetworkRequests.class.getSimpleName();
    
    public final static String BASE_URL_API  = "https://api.github.com";

    // TODO : will be MutableLivedata in next steps
    private ArrayList<UserProfile_Mini> usersProfiles = new ArrayList<>();
    // TODO : only for debug, will use ViewModel/Livedata in next steps
    public ArrayList<UserProfile_Mini> getUsersProfiles(){
        return usersProfiles;
    }

    /**
     * ---------------------------------------------------------------------------------------------
     * Recover user list based on keyword.
     * @param keyword
     */
    public void getUsersSearch(String keyword){
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
                postChanges_usersList(userList);
            }

            @Override
            public void onFailure(Call<UserProfile_Mini_List> call, Throwable t) {
                Log.e(TAG, "onFailure: getUserIdSearch ",  t);
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
                postChanges_usersList(userList);
            }

            @Override
            public void onFailure(Call<UserProfile_Mini_List> call, Throwable t) {
                Log.e(TAG, "onFailure: getUserIdSearch ",  t);
            }
        });
    }


    /**
     * ---------------------------------------------------------------------------------------------
     * Post changes on usersProfiles
     */
     private void postChanges_usersList(ArrayList<UserProfile_Mini> userList){
         usersProfiles = userList;
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
            }

            @Override
            public void onFailure(Call<ArrayList<UserRepository>> call, Throwable t) {
                Log.e(TAG, "onFailure: getUserIdSearch ",  t);
            }
        });
    }





}
