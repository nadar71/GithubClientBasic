package com.mapelli.simone.githubclient.UI;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.mapelli.simone.githubclient.R;
import com.mapelli.simone.githubclient.data.entity.UserProfile_Mini;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class UsersListAdapter extends RecyclerView.Adapter<UsersListAdapter.UsersListViewHolder>{
    private final static String TAG  = UsersListAdapter.class.getSimpleName();
    private SearchUsersActivity parentActivity;
    private List<UserProfile_Mini> usersList;


    private final View.OnClickListener mOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            UserProfile_Mini item = (UserProfile_Mini) view.getTag();

            Context context = view.getContext();
            Intent intent = new Intent(context, UserDetailActivity.class);
            intent.putExtra(UserDetailActivity.ARG_ITEM_ID, item.getLogin());

            context.startActivity(intent);
        }
    };


    public UsersListAdapter(SearchUsersActivity parent,
                                  List<UserProfile_Mini> userList) {
        parentActivity = parent;
        this.usersList = userList;

    }

    @Override
    public UsersListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.user_list_item, parent, false);
        return new UsersListViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull UsersListViewHolder holder, int position) {
        UserProfile_Mini item = usersList.get(position);
        holder.mContentView.setText(item.getLogin());

        Glide.with(parentActivity.getBaseContext())
                .load(item.getAvatar_url())
                .fitCenter()
                .apply(RequestOptions.circleCropTransform())
                .into(holder.userPhoto);

        holder.itemView.setTag(usersList.get(position));
        holder.itemView.setOnClickListener(mOnClickListener);
    }

    @Override
    public int getItemCount() {
        if (usersList == null) return 0;
        return usersList.size();
    }



    /**
     * ---------------------------------------------------------------------------------------------
     * Class Item Holder
     */
    class UsersListViewHolder extends RecyclerView.ViewHolder {
        final TextView mContentView;
        final ImageView userPhoto;

        UsersListViewHolder(View view) {
            super(view);
            mContentView = view.findViewById(R.id.user_name);
            userPhoto    = view.findViewById(R.id.user_photo_img);
        }
    }



    /**
     * ---------------------------------------------------------------------------------------------
     * Init/update the adapter with userList
     * @param userList
     */
    public void setAdapterUserList(List<UserProfile_Mini> userList) {
        this.usersList = userList;
        notifyDataSetChanged();    //refresh recyclerview
    }



    /**
     * ---------------------------------------------------------------------------------------------
     * Reset adapter
     */
    public void resetUsersList() {
        if  (usersList != null) usersList.clear();
    }
}
