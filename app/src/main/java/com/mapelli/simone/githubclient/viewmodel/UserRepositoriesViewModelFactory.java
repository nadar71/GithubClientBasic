package com.mapelli.simone.githubclient.viewmodel;


import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

public class UserRepositoriesViewModelFactory extends ViewModelProvider.NewInstanceFactory {
    private final static String TAG = UserRepositoriesViewModelFactory.class.getSimpleName();

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new UserRepositoriesViewModel();
    }
}
