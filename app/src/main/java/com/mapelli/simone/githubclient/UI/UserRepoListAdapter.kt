package com.mapelli.simone.githubclient.UI


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView

import com.mapelli.simone.githubclient.R
import com.mapelli.simone.githubclient.data.entity.UserRepository
import androidx.recyclerview.widget.RecyclerView

class UserRepoListAdapter(private val parentActivity: UserDetailActivity,
                          private var usersRepoList: List<UserRepository>?)
    : RecyclerView.Adapter<UserRepoListAdapter.UsersRepoListViewHolder>() {


    private val mOnClickListener = View.OnClickListener { view ->
        val item = view.tag as UserRepository
        // do nothing currently TODO : open repository in browser page ? issue #18
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UsersRepoListViewHolder {
        val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.repositories_list_item, parent, false)
        return UsersRepoListViewHolder(view)
    }


    override fun onBindViewHolder(holder: UsersRepoListViewHolder, position: Int) {
        val item = usersRepoList!![position]
        holder.stars_num.text = item.stargazers_count
        holder.repo_name.text = item.name
        // holder.star_img.setImageResource(R.);

        holder.itemView.tag = item
        holder.itemView.setOnClickListener(mOnClickListener)
    }

    override fun getItemCount(): Int {
        return usersRepoList?.size ?: 0
    }


    /**
     * ---------------------------------------------------------------------------------------------
     * Class Item Holder
     */
    class UsersRepoListViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val stars_num: TextView
        val repo_name: TextView
        val star_img: ImageView

        init {
            stars_num = view.findViewById(R.id.stars_num)
            repo_name = view.findViewById(R.id.repo_name)
            star_img = view.findViewById(R.id.stars_img)
        }
    }


    /**
     * ---------------------------------------------------------------------------------------------
     * Init/update the adapter with usersRepoList
     *
     * @param usersRepoList
     */
    fun setAdapterUserList(usersRepoList: List<UserRepository>) {
        this.usersRepoList = usersRepoList
        notifyDataSetChanged()    //refresh recyclerview
    }



    companion object {
        private val TAG = UserRepoListAdapter::class.java.getSimpleName()
    }
}
