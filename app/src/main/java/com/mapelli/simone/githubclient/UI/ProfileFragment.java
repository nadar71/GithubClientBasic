package com.mapelli.simone.githubclient.UI;


import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.mapelli.simone.githubclient.R;
import com.mapelli.simone.githubclient.data.entity.UserProfile_Full;


public class ProfileFragment extends Fragment {
    private ImageView user_photo;
    private TextView  name, location, email, profile_url;
    private String    location_str,email_str;

    private UserDetailActivity parent;
    private UserProfile_Full   currentUser;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_profile, container, false);
        parent = (UserDetailActivity) getActivity();
        currentUser = parent.getCurrentUser();

        user_photo  = rootView.findViewById(R.id.user_photo_img);
        name        = rootView.findViewById(R.id.name_txt);
        location    = rootView.findViewById(R.id.location_txt);
        email       = rootView.findViewById(R.id.email_txt);
        profile_url = rootView.findViewById(R.id.profile_url_txt);


        Glide.with(parent.getBaseContext())
                .load(currentUser.getAvatar_url())
                .fitCenter()
                .into(user_photo);

        location_str = currentUser.getLocation();
        if (location == null) currentUser.setLocation("No location present");

        email_str = currentUser.getEmail();
        if (email_str == null) currentUser.setEmail("No email present");

        name.setText(currentUser.getName());
        location.setText(currentUser.getLocation());
        email.setText(currentUser.getEmail());
        profile_url.setText(currentUser.getRepos_url());


        return rootView;
    }

}
