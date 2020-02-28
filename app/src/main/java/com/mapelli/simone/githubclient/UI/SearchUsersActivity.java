package com.mapelli.simone.githubclient.UI;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;

import androidx.appcompat.widget.Toolbar;

import com.mapelli.simone.githubclient.R;
import com.mapelli.simone.githubclient.data.dummy.DummyContent;


public class SearchUsersActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_list);

        setupActionBar();

        View recyclerView = findViewById(R.id.item_list);
        assert recyclerView != null;
        setupRecyclerView((RecyclerView) recyclerView);
    }


    /**
     * ---------------------------------------------------------------------------------------------
     * Set icons, title etc. in action bar
     * ---------------------------------------------------------------------------------------------
     */
    private void setupActionBar() {
        Toolbar toolbar =  findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle(getString(R.string.search_title));
    }


    private void setupRecyclerView(@NonNull RecyclerView recyclerView) {
        recyclerView.setAdapter(new UsersListAdapter(this, DummyContent.ITEMS));
    }



    // TODO : create my own, set in another file
    /*public static class SimpleItemRecyclerViewAdapter
            extends RecyclerView.Adapter<SimpleItemRecyclerViewAdapter.ViewHolder> {

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

        SimpleItemRecyclerViewAdapter(SearchUsersActivity parent,
                                      List<DummyContent.DummyItem> items)
                                      {
            mValues = items;
            mParentActivity = parent;

        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.user_list_item, parent, false);
            return new ViewHolder(view);
        }


        @Override
        public void onBindViewHolder(final ViewHolder holder, int position) {
            holder.mContentView.setText(mValues.get(position).content);

            holder.itemView.setTag(mValues.get(position));
            holder.itemView.setOnClickListener(mOnClickListener);
        }

        @Override
        public int getItemCount() {
            return mValues.size();
        }

        class ViewHolder extends RecyclerView.ViewHolder {
            final TextView mContentView;

            ViewHolder(View view) {
                super(view);
                mContentView = view.findViewById(R.id.content);
            }
        }
    }*/


    /**
     * ---------------------------------------------------------------------------------------------
     * Menu stuff
     * ---------------------------------------------------------------------------------------------
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.options_menu, menu);

        return true;
    }





}
