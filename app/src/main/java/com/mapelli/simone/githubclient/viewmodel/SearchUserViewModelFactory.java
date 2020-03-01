package com.mapelli.simone.githubclient.viewmodel;


import com.mapelli.simone.githubclient.UI.SearchUsersActivity;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

public class SearchUserViewModelFactory extends ViewModelProvider.NewInstanceFactory {
    private final static String TAG = SearchUsersActivity.class.getSimpleName();


    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new UserSearchViewModel();
    }
}
