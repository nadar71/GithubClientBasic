package com.mapelli.simone.githubclient.UI;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.mapelli.simone.githubclient.R;
import com.mapelli.simone.githubclient.data.entity.UserProfile_Mini;
import com.mapelli.simone.githubclient.data.entity.UserRepository;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class UserRepoListAdapter extends RecyclerView.Adapter<UserRepoListAdapter.UsersRepoListViewHolder> {
    private final static String TAG = UserRepoListAdapter.class.getSimpleName();

    private UserDetailActivity parentActivity;
    private List<UserRepository> usersRepoList;


    private final View.OnClickListener mOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            UserRepository item = (UserRepository) view.getTag();
            // do nothing currently TODO : open repository in browser page ?
        }
    };


    public UserRepoListAdapter(UserDetailActivity parent,
                            List<UserRepository> usersRepoList) {
        parentActivity     = parent;
        this.usersRepoList = usersRepoList;

    }

    @Override
    public UsersRepoListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.repositories_list_item, parent, false);
        return new UsersRepoListViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull UsersRepoListViewHolder holder, int position) {
        UserRepository item = usersRepoList.get(position);
        holder.stars_num.setText(item.getStargazers_count());
        holder.repo_name.setText(item.getName());
        // holder.star_img.setImageResource(R.);

        holder.itemView.setTag(usersRepoList.get(position));
        holder.itemView.setOnClickListener(mOnClickListener);
    }

    @Override
    public int getItemCount() {
        if (usersRepoList == null) return 0;
        return usersRepoList.size();
    }


    /**
     * ---------------------------------------------------------------------------------------------
     * Class Item Holder
     */
    class UsersRepoListViewHolder extends RecyclerView.ViewHolder {
        private TextView  stars_num, repo_name;
        private ImageView star_img;

        UsersRepoListViewHolder(View view) {
            super(view);
            stars_num = view.findViewById(R.id.stars_num);
            repo_name = view.findViewById(R.id.repo_name);
            star_img  = view.findViewById(R.id.stars_img);
        }
    }


    /**
     * ---------------------------------------------------------------------------------------------
     * Init/update the adapter with usersRepoList
     * @param usersRepoList
     */
    public void setAdapterUserList(List<UserRepository> usersRepoList) {
        this.usersRepoList = usersRepoList;
        notifyDataSetChanged();    //refresh recyclerview
    }


    /**
     * ---------------------------------------------------------------------------------------------
     * Reset adapter
     */
    public void resetUsersRepoList() {
        if (usersRepoList != null) usersRepoList.clear();
    }
}
