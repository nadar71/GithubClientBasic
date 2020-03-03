package com.mapelli.simone.githubclient.viewmodel


import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class UserRepositoriesViewModelFactory : ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return UserRepositoriesViewModel() as T
    }

    companion object {
        private val TAG = UserRepositoriesViewModelFactory::class.java!!.getSimpleName()
    }
}
