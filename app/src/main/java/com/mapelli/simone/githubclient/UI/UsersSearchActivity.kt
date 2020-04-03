package com.mapelli.simone.githubclient.UI

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.SearchView
import android.widget.TextView

import com.mapelli.simone.githubclient.R
import com.mapelli.simone.githubclient.data.entity.UserProfile_Mini
import com.mapelli.simone.githubclient.util.MyUtil
import com.mapelli.simone.githubclient.viewmodel.UserSearchViewModelFactory
import com.mapelli.simone.githubclient.viewmodel.UserSearchViewModel

import java.util.ArrayList
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView


class UsersSearchActivity : AppCompatActivity() {

    var userList = mutableListOf<UserProfile_Mini>()
    lateinit var recyclerView: RecyclerView
    lateinit var loadingInProgress: ProgressBar
    lateinit var emptyListText: TextView
    lateinit var toolbarTitle: TextView
    lateinit var adapter: UsersListAdapter

    lateinit var mViewModel: UserSearchViewModel
    lateinit var factory: UserSearchViewModelFactory

    lateinit var loadMore_btn: Button
    var pageCount = 1
    val userPerPage = 30
    var currentSearchingKeywords = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_list)
        setupGenericViews()
        setupActionBar()
        setupRecyclerView()
        setupUsersDataObserver()
        showUserList()
    }


    override fun onResume() {
        super.onResume()
        toolbarTitle.setText(R.string.toolbar_title)
    }

    /**
     * ---------------------------------------------------------------------------------------------
     * Set up progress bar,btn etc.
     */
    private fun setupGenericViews() {
        loadingInProgress = findViewById(R.id.loading_view)
        emptyListText = findViewById(R.id.empty_view)
        toolbarTitle = findViewById(R.id.toolbar_title)


        loadMore_btn = findViewById(R.id.loadMore_btn)
        loadMore_btn.visibility = View.INVISIBLE

        loadMore_btn.setOnClickListener {
            mViewModel.storeUserList(currentSearchingKeywords,
                    Integer.toString(pageCount),
                    Integer.toString(userPerPage))
            pageCount++
        }

    }


    /**
     * ---------------------------------------------------------------------------------------------
     * Set up ViewModel/Livedata for retrieving new users nick list from repo
     */
    private fun setupUsersDataObserver() {
        factory = UserSearchViewModelFactory()
        mViewModel = ViewModelProvider(this, factory).get(UserSearchViewModel::class.java)

        val usersList_observed = mViewModel.userListObserved
        usersList_observed.observe(this, Observer { userEntries ->
            if (userEntries != null && !userEntries.isEmpty()) { // data ready in db
                userList = userEntries as MutableList<UserProfile_Mini>
                val currentPos = adapter.itemCount
                updateAdapter(userEntries)

                showUserList()
                bumpUpList(currentPos)
            } else {
                if (MyUtil.isConnectionOk && !currentSearchingKeywords.isEmpty()) {
                    showLoading()
                } else if (!MyUtil.isConnectionOk) {
                    showNoInternetConnection()
                }
            }
        })
    }


    /**
     * ---------------------------------------------------------------------------------------------
     * Set icons, title etc. in action bar
     */
    private fun setupActionBar() {
        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        toolbar.title = ""
        setSupportActionBar(toolbar)
        // ActionBar actionBar = getSupportActionBar();
        // toolbar.setTitle(getString(R.string.search_title));
    }


    /**
     * --------------------------------------------------------------------------------------------
     * Setup RecyclerView : it starts empty.
     */
    private fun setupRecyclerView() {
        recyclerView = findViewById(R.id.userList)
        recyclerView.layoutManager = LinearLayoutManager(this)

        adapter = UsersListAdapter(this, userList)
        recyclerView.adapter = adapter

        val decoration = DividerItemDecoration(applicationContext,
                LinearLayout.VERTICAL)
        recyclerView.addItemDecoration(decoration)
    }


    /**
     * --------------------------------------------------------------------------------------------
     * Scrollup a bit RecyclerView : useful after new loading by pagination
     */
    private fun bumpUpList(currentPos: Int) {
        recyclerView.post { recyclerView.smoothScrollToPosition(adapter.itemCount - (userPerPage - 2)) }
    }

    /**
     * ---------------------------------------------------------------------------------------------
     * Renew recyclerview data
     * @param userList
     */
    private fun updateAdapter(userList: List<UserProfile_Mini>?) {
        if (!userList!!.isEmpty() && !currentSearchingKeywords.isEmpty()) {
            loadMore_btn.visibility = View.VISIBLE
        }
        adapter.setAdapterUserList(userList)
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
    private fun showUserList() {
        loadingInProgress.visibility = View.INVISIBLE
        recyclerView.visibility = View.VISIBLE

        if (adapter.itemCount <= 0) {
            emptyListText.visibility = View.VISIBLE
            emptyListText.text = "Click on lens and search someone !"
        } else
            emptyListText.visibility = View.INVISIBLE
    }


    /**
     * ---------------------------------------------------------------------------------------------
     * Menu stuff
     * ---------------------------------------------------------------------------------------------
     */
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.options_menu, menu)

        val search_item = menu.findItem(R.id.search)

        val searchView = search_item.actionView as SearchView
        searchView.isFocusable = false
        searchView.queryHint = "Search user"
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {


            override fun onQueryTextSubmit(keywords: String): Boolean {
                showLoading()

                currentSearchingKeywords = keywords
                mViewModel.deleteUserList() // delete previous results
                mViewModel.storeUserList(currentSearchingKeywords,
                        Integer.toString(pageCount),
                        Integer.toString(userPerPage))
                pageCount++

                return false
            }

            override fun onQueryTextChange(s: String): Boolean {
                return false
            }
        })

        return true
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        val id = item.itemId
        toolbarTitle.text = ""

        return super.onOptionsItemSelected(item)
    }

    companion object {
        private val TAG = UsersSearchActivity::class.java.getSimpleName()
    }


}
