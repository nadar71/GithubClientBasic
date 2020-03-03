package com.mapelli.simone.githubclient.UI

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView

import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.mapelli.simone.githubclient.R
import com.mapelli.simone.githubclient.data.entity.UserProfile_Mini
import androidx.recyclerview.widget.RecyclerView

class UsersListAdapter(private val parentActivity: UsersSearchActivity,
                       private var usersList: MutableList<UserProfile_Mini>?) : RecyclerView.Adapter<UsersListAdapter.UsersListViewHolder>() {


    private val mOnClickListener = View.OnClickListener { view ->
        val item = view.tag as UserProfile_Mini

        val context = view.context
        val intent = Intent(context, UserDetailActivity::class.java)
        intent.putExtra(UserDetailActivity.ARG_ITEM_ID, item.login)

        context.startActivity(intent)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UsersListViewHolder {
        val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.user_list_item, parent, false)
        return UsersListViewHolder(view)
    }


    override fun onBindViewHolder(holder: UsersListViewHolder, position: Int) {
        val item = usersList!![position]
        holder.mContentView.text = item.login

        Glide.with(parentActivity.baseContext)
                .load(item.avatar_url)
                .fitCenter()
                .apply(RequestOptions.circleCropTransform())
                .into(holder.userPhoto)

        holder.itemView.tag = usersList!![position]
        holder.itemView.setOnClickListener(mOnClickListener)
    }

    override fun getItemCount(): Int {
        return if (usersList == null) 0 else usersList!!.size
    }


    /**
     * ---------------------------------------------------------------------------------------------
     * Class Item Holder
     */
    internal inner class UsersListViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val mContentView: TextView
        val userPhoto: ImageView

        init {
            mContentView = view.findViewById(R.id.user_name)
            userPhoto = view.findViewById(R.id.user_photo_img)
        }
    }


    /**
     * ---------------------------------------------------------------------------------------------
     * Init/update the adapter with userList
     *
     * @param userList
     */
    fun setAdapterUserList(userList: MutableList<UserProfile_Mini>) {
        this.usersList = userList
        notifyDataSetChanged()    //refresh recyclerview
    }


    /**
     * ---------------------------------------------------------------------------------------------
     * Reset adapter
     */
    fun resetUsersList() {
        if (usersList != null) usersList!!.clear()
    }

    companion object {
        private val TAG = UsersListAdapter::class.java!!.getSimpleName()
    }
}
