package com.mapelli.simone.githubclient.UI


import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter

class DetailSectionAdapter internal constructor(fm: FragmentManager, private val numOfTabs: Int)
    : FragmentPagerAdapter(fm) {

    override fun getItem(position: Int): Fragment {
        when (position) {
            0    -> return ProfileFragment()
            1    -> return RepositoriesFragment()
            else -> return null
        }
    }

    override fun getCount(): Int {
        return numOfTabs
    }

    companion object {
        private val TAG = DetailSectionAdapter::class.java.getSimpleName()
    }

}
