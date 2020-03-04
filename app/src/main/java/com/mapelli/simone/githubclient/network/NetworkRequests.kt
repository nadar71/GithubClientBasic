package com.mapelli.simone.githubclient.network


import android.util.Log

import com.mapelli.simone.githubclient.data.entity.UserProfile_Full
import com.mapelli.simone.githubclient.data.entity.UserProfile_Mini
import com.mapelli.simone.githubclient.data.entity.UserProfile_Mini_List
import com.mapelli.simone.githubclient.data.entity.UserRepository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class NetworkRequests {

    val usersProfiles_Minis = MutableLiveData<List<UserProfile_Mini>>()
    val userProfile_Full    = MutableLiveData<UserProfile_Full>()
    val userRepositories    = MutableLiveData<List<UserRepository>>()



    /*
    val usersProfilesMini: LiveData<List<UserProfile_Mini>>
        get() = usersProfiles_Minis

    val userProfilesFull: LiveData<UserProfile_Full>
        get() = userProfile_Full

    val userRepositories: LiveData<List<UserRepository>>
        get() = userRepositories
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
    */
    fun doUsersSearch(keyword: String, page_num: String, per_page: String) {
        val builder = Retrofit.Builder()
                .baseUrl(BASE_URL_API)
                .addConverterFactory(GsonConverterFactory.create())

        val retrofit = builder.build()
        val client = retrofit.create<NetworkService>(NetworkService::class.java)

        val call = client.usersListSearch_Paging(keyword, page_num, per_page)
        call.enqueue(object : Callback<UserProfile_Mini_List> {
            override fun onResponse(call: Call<UserProfile_Mini_List>,
                                    response: Response<UserProfile_Mini_List>) {
                val result = response.body()
                val userList = result!!.userList
                for (profile in userList!!) {
                    Log.d(TAG, "onResponse: login + " + profile.login +
                            " id : " + profile.id +
                            " avatar_url : " + profile.avatar_url +
                            " login : " + profile.login
                    )
                }

                usersProfiles_Minis.postValue(userList)
            }

            override fun onFailure(call: Call<UserProfile_Mini_List>, t: Throwable) {
                Log.e(TAG, "onFailure: getUserIdSearch ", t)
            }
        })
    }


    /**
     * ---------------------------------------------------------------------------------------------
     * Get single user full profile by login
     *
     * @param login
     */
    fun getUserProfileFullByLogin(login: String) {
        val builder = Retrofit.Builder()
                .baseUrl(BASE_URL_API)
                .addConverterFactory(GsonConverterFactory.create())

        val retrofit = builder.build()
        val client = retrofit.create<NetworkService>(NetworkService::class.java)

        val call = client.userProfileByUserLogin(login)
        call.enqueue(object : Callback<UserProfile_Full> {
            override fun onResponse(call: Call<UserProfile_Full>,
                                    response: Response<UserProfile_Full>) {
                val profile = response.body()
                Log.d(TAG, "onResponse: login + " + profile!!.login +
                        " id : " + profile.id +
                        " avatar_url : " + profile.avatar_url +
                        " email : " + profile.email +
                        " location : " + profile.location +
                        " name : " + profile.name +
                        " repos_url : " + profile.repos_url
                )

                userProfile_Full.postValue(profile)
            }

            override fun onFailure(call: Call<UserProfile_Full>, t: Throwable) {
                Log.e(TAG, "onFailure: getUserProfileFullById ", t)
            }
        })
    }


    /**
     * ---------------------------------------------------------------------------------------------
     * Repo with name in asc/desc direction
     *
     * @param login
     */
    fun getRepoFilterByName_Direction(login: String, direction: String) {
        val builder = Retrofit.Builder()
                .baseUrl(BASE_URL_API)
                .addConverterFactory(GsonConverterFactory.create())

        val retrofit = builder.build()
        val client = retrofit.create<NetworkService>(NetworkService::class.java)

        val call = client.userReposBy_name_direction(login, direction)

        call.enqueue(object : Callback<List<UserRepository>> {
            override fun onResponse(call: Call<List<UserRepository>>,
                                    response: Response<List<UserRepository>>) {
                val listRepo = response.body()
                for (repo in listRepo!!) {
                    repo.user_id_owner = login
                    Log.d(TAG, "onResponse: " +
                            "user_id_owner + " + repo.user_id_owner +
                            "name + " + repo.name +
                            "full_name + " + repo.full_name +
                            "html_url + " + repo.html_url +
                            "created_at + " + repo.created_at +
                            "updated_at + " + repo.updated_at +
                            "pushed_at + " + repo.pushed_at +
                            "stargazers_count + " + repo.stargazers_count +
                            "forks_count + " + repo.forks_count)
                }
                userRepositories.postValue(listRepo)
            }

            override fun onFailure(call: Call<List<UserRepository>>, t: Throwable) {
                Log.e(TAG, "onFailure: getUserIdSearch ", t)
            }
        })
    }


    /**
     * ---------------------------------------------------------------------------------------------
     * Repo with created in asc/desc direction
     *
     * @param login
     */
    fun getRepoFilterByCreated_Direction(login: String, direction: String) {
        val builder = Retrofit.Builder()
                .baseUrl(BASE_URL_API)
                .addConverterFactory(GsonConverterFactory.create())

        val retrofit = builder.build()
        val client = retrofit.create<NetworkService>(NetworkService::class.java)

        val call = client.userReposBy_created_direction(login, "created", direction)
        call.enqueue(object : Callback<List<UserRepository>> {
            override fun onResponse(call: Call<List<UserRepository>>,
                                    response: Response<List<UserRepository>>) {
                val listRepo = response.body()
                for (repo in listRepo!!) {
                    Log.d(TAG, "onResponse:  created_at : " + repo.created_at!!)
                }
                userRepositories.postValue(listRepo)

            }

            override fun onFailure(call: Call<List<UserRepository>>, t: Throwable) {
                Log.e(TAG, "onFailure: getUserIdSearch ", t)
            }
        })
    }


    /**
     * ---------------------------------------------------------------------------------------------
     * Repo with updated in asc/desc direction
     *
     * @param login
     */
    fun getRepoFilterByUpdated_Direction(login: String, direction: String) {
        val builder = Retrofit.Builder()
                .baseUrl(BASE_URL_API)
                .addConverterFactory(GsonConverterFactory.create())

        val retrofit = builder.build()
        val client = retrofit.create<NetworkService>(NetworkService::class.java)

        val call = client.reposForuser_updated_asc(login, "updated", direction)
        call.enqueue(object : Callback<List<UserRepository>> {
            override fun onResponse(call: Call<List<UserRepository>>,
                                    response: Response<List<UserRepository>>) {
                val listRepo = response.body()
                for (repo in listRepo!!) {
                    Log.d(TAG, "onResponse:  updated_at : " + repo.updated_at!!)
                }
                userRepositories.postValue(listRepo)
            }

            override fun onFailure(call: Call<List<UserRepository>>, t: Throwable) {
                Log.e(TAG, "onFailure: getUserIdSearch ", t)
            }
        })
    }


    /**
     * ---------------------------------------------------------------------------------------------
     * Repo with pushed in asc/desc direction
     *
     * @param login
     */
    fun getRepoFilterByPushed_Direction(login: String, direction: String) {
        val builder = Retrofit.Builder()
                .baseUrl(BASE_URL_API)
                .addConverterFactory(GsonConverterFactory.create())

        val retrofit = builder.build()
        val client = retrofit.create<NetworkService>(NetworkService::class.java)

        val call = client.reposForuser_pushed_asc(login, "pushed", direction)
        call.enqueue(object : Callback<List<UserRepository>> {
            override fun onResponse(call: Call<List<UserRepository>>,
                                    response: Response<List<UserRepository>>) {
                val listRepo = response.body()
                for (repo in listRepo!!) {
                    Log.d(TAG, "onResponse:  pushed_at : " + repo.pushed_at!!)
                }
                userRepositories.postValue(listRepo)
            }

            override fun onFailure(call: Call<List<UserRepository>>, t: Throwable) {
                Log.e(TAG, "onFailure: getUserIdSearch ", t)
            }
        })
    }

    companion object {
        private val TAG = NetworkRequests::class.java.getSimpleName()
        val BASE_URL_API = "https://api.github.com"
    }


}
