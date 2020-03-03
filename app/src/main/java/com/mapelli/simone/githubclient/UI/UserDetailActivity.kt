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


class UserDetailActivity : AppCompatActivity() {

    private var toolbar: Toolbar? = null

    private var tabLayout: TabLayout? = null
    private var viewPager: ViewPager? = null
    private var detailSectionAdapter: DetailSectionAdapter? = null
    private var tabProfile: TabItem? = null
    private var tabRepositories: TabItem? = null
    private var user_login: String? = null
    /**
     * ---------------------------------------------------------------------------------------------
     * Used to export the current user to child fragments
     */
    var currentUser: UserProfile_Full? = null
        private set

    private var mViewModel: UserDetailViewModel? = null
    private var factory: UserDetailViewModelFactory? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_detail)

        tabLayout = findViewById(R.id.tablayout)
        tabProfile = findViewById(R.id.tabProfile)
        tabRepositories = findViewById(R.id.tabRepositories)
        viewPager = findViewById(R.id.viewPager)

        val intent = intent
        user_login = intent.getStringExtra(ARG_ITEM_ID)

        setupUpperBar()

        setupUserProfileObserver()
        mViewModel!!.storeUserFull(user_login)


    }

    /**
     * ---------------------------------------------------------------------------------------------
     * Set up ViewModel/Livedata for retrieving new users profile data from repo
     */
    private fun setupUserProfileObserver() {
        factory = UserDetailViewModelFactory()
        mViewModel = ViewModelProvider(this, factory!!).get(UserDetailViewModel::class.java!!)

        val currentUser_observed = mViewModel!!.userObserved
        currentUser_observed.observe(this, Observer { userEntry ->
            if (userEntry != null) {
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
            Log.d(TAG, "currentuser login : " + user_login!!)
            toolbar!!.title = user_login
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
        detailSectionAdapter = DetailSectionAdapter(supportFragmentManager,
                tabLayout!!.tabCount)

        viewPager!!.adapter = detailSectionAdapter
        tabLayout!!.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                viewPager!!.currentItem = tab.position
            }

            override fun onTabUnselected(tab: TabLayout.Tab) {

            }

            override fun onTabReselected(tab: TabLayout.Tab) {

            }
        })
        viewPager!!.addOnPageChangeListener(TabLayout.TabLayoutOnPageChangeListener(tabLayout))
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

        private val TAG = UserDetailActivity::class.java!!.getSimpleName()

        // current user data, toolbar title
        val ARG_ITEM_ID = "item_id"
    }

}
