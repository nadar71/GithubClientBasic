package com.mapelli.simone.githubclient.viewmodel;

import android.util.Log;

import com.mapelli.simone.githubclient.UI.SearchUsersActivity;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

public class SearchUserViewModelFactory extends ViewModelProvider.NewInstanceFactory {
    private final static String TAG = SearchUsersActivity.class.getSimpleName();

    private final String keyword;
    private final String page_num;
    private final String per_page;


    public SearchUserViewModelFactory(String keyword, String page_num, String per_page) {
        Log.d(TAG, "userMini_List_fromNet: CALLED");
        this.keyword  = keyword;
        this.page_num = page_num;
        this.per_page = per_page;

    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new UserSearchViewModel(keyword, page_num, per_page);
    }
}
