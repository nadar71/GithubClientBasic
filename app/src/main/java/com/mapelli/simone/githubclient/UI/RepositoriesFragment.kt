package com.mapelli.simone.githubclient.UI


import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.TextView

import com.mapelli.simone.githubclient.R
import com.mapelli.simone.githubclient.data.entity.UserProfile_Full
import com.mapelli.simone.githubclient.data.entity.UserRepository
import com.mapelli.simone.githubclient.util.MyUtil
import com.mapelli.simone.githubclient.viewmodel.UserRepositoriesViewModel
import com.mapelli.simone.githubclient.viewmodel.UserRepositoriesViewModelFactory
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView


class RepositoriesFragment : Fragment() {
    lateinit var title_list: TextView
    lateinit var filter_type: TextView
    lateinit var filter_btn: Button
    lateinit var asc_order_btn: Button
    lateinit var desc_order_btn: Button
    lateinit var loadingInProgress: ProgressBar
    lateinit var emptyListText: TextView
    lateinit var parentContext: Context

    lateinit var parent: UserDetailActivity
    lateinit var currentUser: UserProfile_Full
    var user_login: String? = null

    var userRepoList: List<UserRepository>? = null
    lateinit var recyclerView: RecyclerView
    lateinit var adapter: UserRepoListAdapter

    lateinit var mViewModel: UserRepositoriesViewModel
    lateinit var factory: UserRepositoriesViewModelFactory

    var FILTER_TYPE = "name"

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        val rootView = inflater.inflate(R.layout.fragment_repositories, container, false)
        parent = activity as UserDetailActivity
        currentUser = parent.currentUser
        user_login = currentUser.login

        parentContext = parent.baseContext

        title_list = rootView.findViewById(R.id.title_list_txt)
        filter_type = rootView.findViewById(R.id.filter_type_txt)

        loadingInProgress = rootView.findViewById(R.id.loading_view)
        emptyListText = rootView.findViewById(R.id.empty_view)

        setupRecyclerView(rootView)
        setupButton(rootView, parentContext)

        setupRepoListObserver()
        mViewModel.storeUserRepoList_ByName(user_login, "asc")


        return rootView
    }


    /**
     * ---------------------------------------------------------------------------------------------
     * Set up ViewModel/Livedata for retrieving new users repository data from repo
     */
    private fun setupRepoListObserver() {
        // set up viewmodel/livedata to observe data in db and ask for new ones to repo
        factory = UserRepositoriesViewModelFactory()
        mViewModel = ViewModelProvider(this, factory).get(UserRepositoriesViewModel::class.java)

        val usersList_observed = mViewModel.userRepoListObserved
        usersList_observed.observe(parent, Observer { repoEntries ->
            if (repoEntries != null && !repoEntries.isEmpty()) { // data ready in db
                userRepoList = repoEntries
                updateAdapter(repoEntries)
                showRepoList()
            } else {                                                         // waiting for data
                if (MyUtil.isConnectionOk) {
                    showLoading()
                } else {
                    showNoInternetConnection()
                }
            }
        })
    }


    /**
     * --------------------------------------------------------------------------------------------
     * Button creation and setup
     * @param rootView
     * @param parentContext
     */
    private fun setupButton(rootView: View, parentContext: Context) {
        filter_btn = rootView.findViewById(R.id.filter_btn)
        asc_order_btn = rootView.findViewById(R.id.asc_order_btn)
        desc_order_btn = rootView.findViewById(R.id.desc_order_btn)


        filter_btn.setOnClickListener { showAlertDialog() }

        asc_order_btn.setOnClickListener {
            filter_type.text = parentContext.getString(R.string.filter_type_start_label) +
                    FILTER_TYPE + " asc "
            when (FILTER_TYPE) {
                "name"          -> mViewModel.storeUserRepoList_ByName(user_login!!, "asc")
                "data creation" -> mViewModel.storeUserRepoList_ByCreated(user_login!!, "asc")
                "data update"   -> mViewModel.storeUserRepoList_ByUpdated(user_login!!, "asc")
                "data pushed"   -> mViewModel.storeUserRepoList_ByPushed(user_login!!, "asc")
            }
        }


        desc_order_btn.setOnClickListener {
            filter_type.text = parentContext.getString(R.string.filter_type_start_label) +
                    FILTER_TYPE + " desc "
            when (FILTER_TYPE) {
                "name" -> mViewModel.storeUserRepoList_ByName(user_login!!, "desc")
                "data creation" -> mViewModel.storeUserRepoList_ByCreated(user_login!!, "desc")
                "data update"   -> mViewModel.storeUserRepoList_ByUpdated(user_login!!, "desc")
                "data pushed"   -> mViewModel.storeUserRepoList_ByPushed(user_login!!, "desc")
            }
        }

    }


    /**
     * --------------------------------------------------------------------------------------------
     * Setup RecyclerView : it starts with currnt user data repos
     */
    private fun setupRecyclerView(rootView: View) {
        recyclerView = rootView.findViewById(R.id.repo_list)
        recyclerView.layoutManager = LinearLayoutManager(parent.baseContext)

        adapter = UserRepoListAdapter(parent, userRepoList)
        recyclerView.adapter = adapter

        val decoration = DividerItemDecoration(parent.baseContext,
                LinearLayout.VERTICAL)
        recyclerView.addItemDecoration(decoration)

    }

    /**
     * ---------------------------------------------------------------------------------------------
     * Renew recyclerview data
     * @param userRepoList
     */
    private fun updateAdapter(userRepoList: List<UserRepository>) {
        adapter.setAdapterUserList(userRepoList)
    }


    /**
     * ---------------------------------------------------------------------------------------------
     * Alert dialog for setting filter type
     */
    private fun showAlertDialog() {
        val alert: AlertDialog
        val alertDialog = AlertDialog.Builder(parent)
        alertDialog.setTitle(parentContext.getString(R.string.alert_filter_title))

        val items = arrayOf("name", "data creation", "data update", "data pushed")
        var checkedItem = 0
        when (FILTER_TYPE) {
            "data creation" -> checkedItem = 1
            "data update"   -> checkedItem = 2
            "data pushed"   -> checkedItem = 3
            else            -> checkedItem = 0
        }
        alertDialog.setSingleChoiceItems(items, checkedItem) { dialog, which ->
            when (which) {
                0 -> FILTER_TYPE = "name"
                1 -> FILTER_TYPE = "data creation"
                2 -> FILTER_TYPE = "data update"
                3 -> FILTER_TYPE = "data pushed"
            }
        }
        alert = alertDialog.create()
        alert.setCanceledOnTouchOutside(true)

        alert.show()

    }


    /**
     * ---------------------------------------------------------------------------------------------
     * Show No Internet Connection view
     */
    private fun showNoInternetConnection() {
        loadingInProgress.visibility = View.GONE
        recyclerView.visibility = View.GONE
        emptyListText.visibility = View.VISIBLE
        emptyListText.setText(R.string.no_connection)
    }

    /**
     * ---------------------------------------------------------------------------------------------
     * Show loading in progress view, hiding  list
     */
    private fun showLoading() {
        loadingInProgress.visibility = View.VISIBLE
        recyclerView.visibility = View.GONE
        emptyListText.visibility = View.VISIBLE
        emptyListText.setText(R.string.searching)
    }

    /**
     * ---------------------------------------------------------------------------------------------
     * Show list after loading/retrieving data completed
     */
    private fun showRepoList() {
        loadingInProgress.visibility = View.INVISIBLE
        recyclerView.visibility = View.VISIBLE
        emptyListText.visibility = View.INVISIBLE
    }

    companion object {
        private val TAG = RepositoriesFragment::class.java.getSimpleName()
    }
}
