package com.kamathtanay.kblock.ui.mainscreen

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.widget.EditText
import android.widget.SearchView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.google.android.material.tabs.TabLayoutMediator
import com.kamathtanay.kblock.R
import com.kamathtanay.kblock.data.db.AppDatabase
import com.kamathtanay.kblock.data.repository.BlockedRepository
import com.kamathtanay.kblock.databinding.ActivityMainBinding
import com.kamathtanay.kblock.service.PhoneStateService
import com.kamathtanay.kblock.ui.mainscreen.adapters.MainViewPagerAdapter
import com.kamathtanay.kblock.ui.mainscreen.blockedtab.BlockedContactsFragment
import com.kamathtanay.kblock.ui.mainscreen.contactstab.UserContactsFragment
import com.kamathtanay.kblock.ui.mainscreen.logstab.BlockedCallLogFragment


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val blockedContactsFragment: BlockedContactsFragment = BlockedContactsFragment()
    private val userContactsFragment: UserContactsFragment = UserContactsFragment()
    private val blockedCallsLogFragment: BlockedCallLogFragment = BlockedCallLogFragment()

    private val tabTitleArray = arrayOf(
        "Blocked",
        "Contacts",
        "Logs"
    )

    private val tabIconArray = arrayOf(
        R.drawable.ic_baseline_block_24,
        R.drawable.ic_baseline_people_24,
        R.drawable.ic_disconnect
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        setSupportActionBar(binding.appBarMain)

        val callBlockerServiceIntent = Intent(this, PhoneStateService::class.java)
        ContextCompat.startForegroundService(this, callBlockerServiceIntent);

        val mainViewPager = binding.tabsPagerMain
        val mainTabLayout = binding.tabLayoutMain

        val mainViewPagerAdapter = MainViewPagerAdapter(
            supportFragmentManager,
            lifecycle,
            blockedContactsFragment,
            userContactsFragment,
            blockedCallsLogFragment
        )
        mainViewPager.adapter = mainViewPagerAdapter

        TabLayoutMediator(mainTabLayout, mainViewPager) { tab, position ->
            tab.text = tabTitleArray[position]
            tab.icon = ContextCompat.getDrawable(applicationContext, tabIconArray[position])
        }.attach()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.activity_main_menu, menu)
        val searchItem = menu?.findItem(R.id.main_search_bar)
        val searchView: SearchView = searchItem?.actionView as SearchView
        val searchSrcTextId = resources.getIdentifier("android:id/search_src_text", null, null)
        val searchTextView = searchView.findViewById(searchSrcTextId) as EditText
        searchTextView.setTextColor(ContextCompat.getColor(this, R.color.white))
        searchTextView.setHintTextColor(ContextCompat.getColor(this, R.color.white))
        searchTextView.textSize = 16F
        return true
    }

}