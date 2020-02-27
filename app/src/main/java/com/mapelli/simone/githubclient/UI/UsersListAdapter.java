package com.mapelli.simone.githubclient.UI;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mapelli.simone.githubclient.R;
import com.mapelli.simone.githubclient.dummy.DummyContent;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class UsersListAdapter extends RecyclerView.Adapter<UsersListAdapter.UsersListViewHolder>{

    private final SearchUsersActivity mParentActivity;
    private final List<DummyContent.DummyItem> mValues;
    
    private final View.OnClickListener mOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            DummyContent.DummyItem item = (DummyContent.DummyItem) view.getTag();


            Context context = view.getContext();
            Intent intent = new Intent(context, UserDetailActivity.class);
            intent.putExtra(UserDetailActivity.ARG_ITEM_ID, item.id);

            context.startActivity(intent);
        }
    };

    UsersListAdapter(SearchUsersActivity parent,
                                  List<DummyContent.DummyItem> items)
    {
        mValues = items;
        mParentActivity = parent;

    }

    @Override
    public UsersListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.user_list_item, parent, false);
        return new UsersListViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull UsersListViewHolder holder, int position) {
        holder.mContentView.setText(mValues.get(position).content);

        holder.itemView.setTag(mValues.get(position));
        holder.itemView.setOnClickListener(mOnClickListener);
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    class UsersListViewHolder extends RecyclerView.ViewHolder {
        final TextView mContentView;

        UsersListViewHolder(View view) {
            super(view);
            mContentView = view.findViewById(R.id.content);
        }
    }
}
