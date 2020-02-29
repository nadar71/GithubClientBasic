package com.mapelli.simone.githubclient.network;


import android.util.Log;
import android.widget.ListView;
import android.widget.Toast;

import com.mapelli.simone.githubclient.data.entity.UserProfile_Full;
import com.mapelli.simone.githubclient.data.entity.UserProfile_Mini;
import com.mapelli.simone.githubclient.data.entity.UserProfile_Mini_List;
import com.mapelli.simone.githubclient.data.entity.UserRepository;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class NetworkRequests {

    private final static String TAG  =  NetworkRequests.class.getSimpleName();

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
                .baseUrl("https://api.github.com")
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
     * Get repo list for user
     * By default name are in asc direction.
     * @param login
     */
    public void getUserRepo(String login){
        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl("https://api.github.com")
                .addConverterFactory(GsonConverterFactory.create());

        Retrofit retrofit = builder.build();
        NetworkService client = retrofit.create(NetworkService.class);

        Call<List<UserRepository>> call = client.reposForuser(login);
        call.enqueue(new Callback<List<UserRepository>>() {
            @Override
            public void onResponse(Call<List<UserRepository>> call,
                                   Response<List<UserRepository>> response) {
                List<UserRepository> listRepo = response.body();
                for(UserRepository repo:listRepo){
                    Log.d(TAG, "onResponse: name + "+repo.getName());
                }
            }

            @Override
            public void onFailure(Call<List<UserRepository>> call, Throwable t) {
                Log.e(TAG, "onFailure: getUserProfileFullById ",  t);
            }
        });

    }


    /**
     * ---------------------------------------------------------------------------------------------
     * Repo with name in desc direction
     * @param user
     */
    public void getRepo_Name_Desc(String user){
        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl("https://api.github.com")
                .addConverterFactory(GsonConverterFactory.create());

        Retrofit retrofit = builder.build();
        NetworkService client = retrofit.create(NetworkService.class);

        Call<List<UserRepository>> call = client.reposForuser_name_desc(user,"desc");
        call.enqueue(new Callback<List<UserRepository>>() {
            @Override
            public void onResponse(Call<List<UserRepository>> call,
                                   Response<List<UserRepository>> response) {
                List<UserRepository> listRepo = response.body();
                for(UserRepository repo:listRepo){
                    Log.d(TAG, "onResponse: name + "+repo.getName());
                }
            }

            @Override
            public void onFailure(Call<List<UserRepository>> call, Throwable t) {
                Log.e(TAG, "onFailure: getUserIdSearch ",  t);
            }
        });
    }



    /**
     * ---------------------------------------------------------------------------------------------
     * Repo with created in asc direction
     * @param user
     */
    public void getRepo_created_Asc(String user){
        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl("https://api.github.com")
                .addConverterFactory(GsonConverterFactory.create());

        Retrofit retrofit = builder.build();
        NetworkService client = retrofit.create(NetworkService.class);

        Call<List<UserRepository>> call = client.reposForuser_created_asc(user,"created",
                "asc");
        call.enqueue(new Callback<List<UserRepository>>() {
            @Override
            public void onResponse(Call<List<UserRepository>> call,
                                   Response<List<UserRepository>> response) {
                List<UserRepository> listRepo = response.body();
                for(UserRepository repo:listRepo){
                    Log.d(TAG, "onResponse:  created_at : "+repo.getCreated_at());
                }
            }

            @Override
            public void onFailure(Call<List<UserRepository>> call, Throwable t) {
                Log.e(TAG, "onFailure: getUserIdSearch ",  t);
            }
        });
    }



    /**
     * ---------------------------------------------------------------------------------------------
     * Repo with created in desc direction
     * @param user
     */
    public void getRepo_created_Desc(String user){
        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl("https://api.github.com")
                .addConverterFactory(GsonConverterFactory.create());

        Retrofit retrofit = builder.build();
        NetworkService client = retrofit.create(NetworkService.class);

        Call<List<UserRepository>> call = client.reposForuser_created_asc(user,"created",
                "desc");
        call.enqueue(new Callback<List<UserRepository>>() {
            @Override
            public void onResponse(Call<List<UserRepository>> call,
                                   Response<List<UserRepository>> response) {
                List<UserRepository> listRepo = response.body();
                for(UserRepository repo:listRepo){
                    Log.d(TAG, "onResponse:  created_at : "+repo.getCreated_at());
                }
            }

            @Override
            public void onFailure(Call<List<UserRepository>> call, Throwable t) {
                Log.e(TAG, "onFailure: getUserIdSearch ",  t);
            }
        });
    }



    /**
     * ---------------------------------------------------------------------------------------------
     * Repo with updated in asc direction
     * @param user
     */
    public void getRepo_updated_Asc(String user){
        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl("https://api.github.com")
                .addConverterFactory(GsonConverterFactory.create());

        Retrofit retrofit = builder.build();
        NetworkService client = retrofit.create(NetworkService.class);

        Call<List<UserRepository>> call = client.reposForuser_updated_asc(user,"updated",
                "asc");
        call.enqueue(new Callback<List<UserRepository>>() {
            @Override
            public void onResponse(Call<List<UserRepository>> call,
                                   Response<List<UserRepository>> response) {
                List<UserRepository> listRepo = response.body();
                for(UserRepository repo:listRepo){
                    Log.d(TAG, "onResponse:  updated_at : "+repo.getUpdated_at());
                }
            }

            @Override
            public void onFailure(Call<List<UserRepository>> call, Throwable t) {
                Log.e(TAG, "onFailure: getUserIdSearch ",  t);
            }
        });
    }




    /**
     * ---------------------------------------------------------------------------------------------
     * Repo with updated in desc direction
     * @param user
     */
    public void getRepo_updated_Desc(String user){
        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl("https://api.github.com")
                .addConverterFactory(GsonConverterFactory.create());

        Retrofit retrofit = builder.build();
        NetworkService client = retrofit.create(NetworkService.class);

        Call<List<UserRepository>> call = client.reposForuser_updated_asc(user,"updated",
                "desc");
        call.enqueue(new Callback<List<UserRepository>>() {
            @Override
            public void onResponse(Call<List<UserRepository>> call,
                                   Response<List<UserRepository>> response) {
                List<UserRepository> listRepo = response.body();
                for(UserRepository repo:listRepo){
                    Log.d(TAG, "onResponse:  updated_at : "+repo.getUpdated_at());
                }
            }

            @Override
            public void onFailure(Call<List<UserRepository>> call, Throwable t) {
                Log.e(TAG, "onFailure: getUserIdSearch ",  t);
            }
        });
    }



    /**
     * ---------------------------------------------------------------------------------------------
     * Repo with pushed in asc direction
     * @param user
     */
    public void getRepo_pushed_Asc(String user){
        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl("https://api.github.com")
                .addConverterFactory(GsonConverterFactory.create());

        Retrofit retrofit = builder.build();
        NetworkService client = retrofit.create(NetworkService.class);

        Call<List<UserRepository>> call = client.reposForuser_pushed_asc(user,"pushed",
                "asc");
        call.enqueue(new Callback<List<UserRepository>>() {
            @Override
            public void onResponse(Call<List<UserRepository>> call,
                                   Response<List<UserRepository>> response) {
                List<UserRepository> listRepo = response.body();
                for(UserRepository repo:listRepo){
                    Log.d(TAG, "onResponse:  pushed_at : "+repo.getPushed_at());
                }
            }

            @Override
            public void onFailure(Call<List<UserRepository>> call, Throwable t) {
                Log.e(TAG, "onFailure: getUserIdSearch ",  t);
            }
        });
    }



    /**
     * ---------------------------------------------------------------------------------------------
     * Repo with pushed in desc direction
     * @param user
     */
    public void getRepo_pushed_Desc(String user){
        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl("https://api.github.com")
                .addConverterFactory(GsonConverterFactory.create());

        Retrofit retrofit = builder.build();
        NetworkService client = retrofit.create(NetworkService.class);

        Call<List<UserRepository>> call = client.reposForuser_pushed_desc(user,"pushed",
                "desc");
        call.enqueue(new Callback<List<UserRepository>>() {
            @Override
            public void onResponse(Call<List<UserRepository>> call,
                                   Response<List<UserRepository>> response) {
                List<UserRepository> listRepo = response.body();
                for(UserRepository repo:listRepo){
                    Log.d(TAG, "onResponse:  pushed_at : "+repo.getPushed_at());
                }
            }

            @Override
            public void onFailure(Call<List<UserRepository>> call, Throwable t) {
                Log.e(TAG, "onFailure: getUserIdSearch ",  t);
            }
        });
    }


}
