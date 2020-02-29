package com.mapelli.simone.githubclient.UI;


import android.app.Activity;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.mapelli.simone.githubclient.R;


public class ProfileFragment extends Fragment {
    ImageView user_photo;
    TextView  name;
    TextView  location;
    TextView  email;
    TextView  profile_url;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_profile, container, false);

        user_photo  = rootView.findViewById(R.id.user_photo_img);
        name        = rootView.findViewById(R.id.name_txt);
        location    = rootView.findViewById(R.id.location_txt);
        email       = rootView.findViewById(R.id.email_txt);
        profile_url = rootView.findViewById(R.id.profile_url_txt);


        UserDetailActivity parent = (UserDetailActivity) getActivity();

        Glide.with(parent.getBaseContext())
                .load(parent.getCurrentUser().getAvatar_url())
                .fitCenter()
                .into(user_photo);

        name.setText(parent.getCurrentUser().getName());
        location.setText(parent.getCurrentUser().getLocation());
        email.setText(parent.getCurrentUser().getEmail());
        profile_url.setText(parent.getCurrentUser().getRepos_url());


        return rootView;
    }

}
