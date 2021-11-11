package com.kamathtanay.kblock.ui.mainscreen.adapters

import androidx.lifecycle.Lifecycle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.kamathtanay.kblock.ui.mainscreen.blockedtab.BlockedContactsFragment
import com.kamathtanay.kblock.ui.mainscreen.contactstab.UserContactsFragment
import com.kamathtanay.kblock.ui.mainscreen.logstab.BlockedCallLogFragment

private const val NUM_TABS = 3

class MainViewPagerAdapter(
    fragmentManager: FragmentManager,
    lifecycle: Lifecycle,
    private val blockedTabFragment: BlockedContactsFragment,
    private val contactsTabFragment: UserContactsFragment,
    val logsTabFragment: BlockedCallLogFragment) : FragmentStateAdapter(fragmentManager, lifecycle) {

    override fun getItemCount(): Int {
        return NUM_TABS
    }

    override fun createFragment(position: Int): Fragment {
        when (position) {
            0 -> return blockedTabFragment
            1 -> return contactsTabFragment
            2 -> return logsTabFragment
        }
        return blockedTabFragment
    }
}