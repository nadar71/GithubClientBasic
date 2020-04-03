package com.mapelli.simone.githubclient.UI

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.MenuItem

import com.google.android.material.tabs.TabItem
import com.google.android.material.tabs.TabLayout
import com.mapelli.simone.githubclient.R
import com.mapelli.simone.githubclient.data.entity.UserProfile_Full
import com.mapelli.simone.githubclient.viewmodel.UserDetailViewModel
import com.mapelli.simone.githubclient.viewmodel.UserDetailViewModelFactory
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.app.NavUtils
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager.widget.ViewPager
import kotlinx.android.synthetic.main.activity_user_detail.*


class UserDetailActivity : AppCompatActivity() {

    lateinit var toolbar: Toolbar

    lateinit var tabLayout: TabLayout
    lateinit var viewPager: ViewPager
    lateinit var detailSectionAdapter: DetailSectionAdapter
    var tabProfile: TabItem? = null
    var tabRepositories: TabItem? = null
    lateinit var user_login: String

    /**
     * ---------------------------------------------------------------------------------------------
     * Used to export the current user to child fragments
     */
    lateinit var currentUser: UserProfile_Full

    lateinit var mViewModel: UserDetailViewModel
    lateinit var factory: UserDetailViewModelFactory


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_detail)
        getLoginFromCaller()
        setupUserProfileObserver()
        mViewModel.storeUserFull(user_login)
        Log.d(TAG, "CHECK : stored user data for login = $user_login")
        setupLayoutViews()
    }

    /**
     * ---------------------------------------------------------------------------------------------
     * Setup all the view in the activity
     */
    private fun setupLayoutViews() {
        tabLayout = findViewById(R.id.tablayout)
        tabProfile = findViewById(R.id.tabProfile)
        tabRepositories = findViewById(R.id.tabRepositories)
        viewPager = findViewById(R.id.viewPager)
        setupUpperBar()
    }

    /**
     * ---------------------------------------------------------------------------------------------
     * Get the login info passed by the parent actvity
     */
    private fun getLoginFromCaller() {
        val intent = intent
        user_login = intent.getStringExtra(ARG_ITEM_ID)
        Log.d(TAG, "CHECK : user_login : $user_login")
    }

    /**
     * ---------------------------------------------------------------------------------------------
     * Set up ViewModel/Livedata for retrieving new users profile data from repo
     */
    private fun setupUserProfileObserver() {
        factory    = UserDetailViewModelFactory()
        mViewModel = ViewModelProvider(this, factory).get(UserDetailViewModel::class.java)

        val currentUser_observed = mViewModel.userObserved
        currentUser_observed.observe(this, Observer { userEntry ->
            if (userEntry != null) {
                Log.d(TAG, "CHECK : userEntry.login : ${userEntry.login}")
                currentUser = userEntry
                setupViewPager()
            }
        })
    }


    /**
     * ---------------------------------------------------------------------------------------------
     * Set icons, title etc. in action bar
     */
    private fun setupUpperBar() {
        toolbar = findViewById(R.id.detail_toolbar)

        if (user_login !== "") {
            Log.d(TAG, "CHECK : currentuser login : " + user_login)
            toolbar.title = user_login
        }

        setSupportActionBar(toolbar)

        val actionBar = supportActionBar
        actionBar?.setDisplayHomeAsUpEnabled(true)


    }


    /**
     * ---------------------------------------------------------------------------------------------
     * Setup adapter and ViewPager
     */
    private fun setupViewPager() {
        Log.d(TAG, "CHECK : setupViewPager init")

        detailSectionAdapter = DetailSectionAdapter(supportFragmentManager,
                tabLayout.tabCount)

        viewPager.adapter = detailSectionAdapter
        viewPager.currentItem = 0

        tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                viewPager.currentItem = tab.position
            }

            override fun onTabUnselected(tab: TabLayout.Tab) {

            }

            override fun onTabReselected(tab: TabLayout.Tab) {

            }
        })
        viewPager.addOnPageChangeListener(TabLayout.TabLayoutOnPageChangeListener(tabLayout))
    }


    /**
     * ---------------------------------------------------------------------------------------------
     * Home button setup
     * @param item
     * @return
     */
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        if (id == android.R.id.home) {
            // This ID represents the Home or Up button. In the case of this
            // activity, the Up button is shown. Use NavUtils to allow users
            // to navigate up one level in the application structure. For
            // more details, see the Navigation pattern on Android Design:
            //
            // http://developer.android.com/design/patterns/navigation.html#up-vs-back
            //
            NavUtils.navigateUpTo(this, Intent(this, UsersSearchActivity::class.java))
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    companion object {
        private val TAG = UserDetailActivity::class.java.getSimpleName()
        // current user data, toolbar title
        val ARG_ITEM_ID = "item_id"
    }

}
