package com.mapelli.simone.githubclient.viewmodel;


import com.mapelli.simone.githubclient.UI.UsersSearchActivity;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

public class UserSearchViewModelFactory extends ViewModelProvider.NewInstanceFactory {
    private final static String TAG = UsersSearchActivity.class.getSimpleName();


    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new UserSearchViewModel();
    }
}
