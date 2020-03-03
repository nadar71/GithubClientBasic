package com.mapelli.simone.githubclient.viewmodel


import com.mapelli.simone.githubclient.UI.UsersSearchActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class UserSearchViewModelFactory : ViewModelProvider.NewInstanceFactory() {


    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return UserSearchViewModel() as T
    }

    companion object {
        private val TAG = UsersSearchActivity::class.java!!.getSimpleName()
    }
}
