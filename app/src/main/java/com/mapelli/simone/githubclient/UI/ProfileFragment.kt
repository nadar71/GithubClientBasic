package com.mapelli.simone.githubclient.UI


import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView

import com.bumptech.glide.Glide
import com.mapelli.simone.githubclient.R
import com.mapelli.simone.githubclient.data.entity.UserProfile_Full


class ProfileFragment : Fragment() {
    lateinit var user_photo: ImageView
    lateinit var name: TextView
    lateinit var location: TextView
    lateinit var email: TextView
    lateinit var profile_url: TextView
    lateinit var location_str: String
    lateinit var email_str: String

    lateinit var parent: UserDetailActivity
    lateinit var currentUser: UserProfile_Full

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        Log.d(TAG, "CHECK : inside ProfileFragment")
        val rootView = inflater.inflate(R.layout.fragment_profile, container, false)
        parent = activity as UserDetailActivity
        currentUser = parent.currentUser

        user_photo = rootView.findViewById(R.id.user_photo_img)
        name = rootView.findViewById(R.id.name_txt)
        location = rootView.findViewById(R.id.location_txt)
        email = rootView.findViewById(R.id.email_txt)
        profile_url = rootView.findViewById(R.id.profile_url_txt)

        location_str = currentUser.location!!
        currentUser.location ?: "No location present"

        email_str = currentUser.email!!
        currentUser.email ?: "No email present"

        name.text = currentUser.name
        location.text = currentUser.location
        email.text = currentUser.email
        profile_url.text = currentUser.repos_url

        Glide.with(parent.baseContext)
                .load(currentUser.avatar_url)
                .fitCenter()
                .into(user_photo)


        return rootView
    }


    companion object {
        private val TAG = ProfileFragment::class.java.getSimpleName()
    }

}
