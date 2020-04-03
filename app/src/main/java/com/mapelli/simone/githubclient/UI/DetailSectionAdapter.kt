package com.mapelli.simone.githubclient.UI


import android.util.Log
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter

class DetailSectionAdapter internal constructor(fm: FragmentManager, private val numOfTabs: Int)
    : FragmentPagerAdapter(fm,FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {


    override fun getItem(position: Int): Fragment {
        Log.d(TAG, "CHECK : position : $position")

        when (position) {
            0    -> {
                Log.d(TAG, "CHECK : position is 0 go to ProfileFragment")
                return ProfileFragment()
            }
            1    -> {
                Log.d(TAG, "CHECK : position is 1 go to RepositoriesFragment")
                return RepositoriesFragment()
            }
            else -> return ProfileFragment()     // TODO : only as placeholder, find a better choice
        }


/*      if (position == 0) return ProfileFragment()
        else return RepositoriesFragment()*/

    }

    override fun getCount(): Int {
        return numOfTabs
    }

    companion object {
        private val TAG = DetailSectionAdapter::class.java.getSimpleName()
    }

}
