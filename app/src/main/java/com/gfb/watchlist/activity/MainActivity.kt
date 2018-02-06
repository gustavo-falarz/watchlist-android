package com.gfb.watchlist.activity

import android.content.Intent
import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.design.widget.TabLayout
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import android.support.v4.view.GravityCompat
import android.support.v4.widget.DrawerLayout
import android.support.v7.app.ActionBarDrawerToggle
import android.view.MenuItem
import android.widget.TextView
import com.gfb.watchlist.R
import com.gfb.watchlist.entity.ContentContainer
import com.gfb.watchlist.entity.UserInfo
import com.gfb.watchlist.entity.dto.UserContentDTO
import com.gfb.watchlist.fragment.MoviesFragment
import com.gfb.watchlist.fragment.RecentlyAddedFragment
import com.gfb.watchlist.fragment.SeriesFragment
import com.gfb.watchlist.service.ContentService
import com.gfb.watchlist.util.Constants
import kotlinx.android.synthetic.main.app_bar_main.*
import kotlinx.android.synthetic.main.content_main.*
import org.jetbrains.anko.alert
import org.jetbrains.anko.startActivity


@Suppress("DEPRECATION")
class MainActivity : BaseActivity(), NavigationView.OnNavigationItemSelectedListener {

    private var mSectionsPagerAdapter: MainActivity.SectionsPagerAdapter? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupToolbar(R.string.app_name)

        mSectionsPagerAdapter = SectionsPagerAdapter(supportFragmentManager)

        container.addOnPageChangeListener(TabLayout.TabLayoutOnPageChangeListener(tabs))
        tabs.addOnTabSelectedListener(TabLayout.ViewPagerOnTabSelectedListener(container))

        val drawer = findViewById<DrawerLayout>(R.id.drawer_layout)
        val toggle = ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        drawer.setDrawerListener(toggle)
        toggle.syncState()

        val navigationView = findViewById<NavigationView>(R.id.nav_view)
        navigationView.setNavigationItemSelectedListener(this)
        val header = navigationView.getHeaderView(0)
        val textUser = header.findViewById<TextView>(R.id.textView)
        textUser.text = UserInfo.email

        fab.setOnClickListener { startActivity<AddToListActivity>() }

    }

    override fun onBackPressed() {
        val drawer = findViewById<DrawerLayout>(R.id.drawer_layout)
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }


    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        val id = item.itemId

        when (id) {
            R.id.nav_add_content -> {
                startActivity<AddToListActivity>()
            }
            R.id.nav_archive -> {
                startActivity<ArchiveActivity>()
            }
            R.id.nav_password -> {
                val intent = Intent(baseContext, ChangePasswordActivity::class.java)
                intent.putExtra(Constants.TRANSITION_KEY_CONTENT, UserInfo.email)
                startActivity(intent)
            }
            R.id.nav_logout -> {
                logoutConfirmation()
            }
        }

        val drawer = findViewById<DrawerLayout>(R.id.drawer_layout)
        drawer.closeDrawer(GravityCompat.START)
        return false
    }

    inner class SectionsPagerAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {

        override fun getItem(position: Int): Fragment {
            when (position) {
                0 -> {
                    return RecentlyAddedFragment.newInstance()
                }
                1 -> {
                    return MoviesFragment.newInstance()
                }
                2 -> {
                    return SeriesFragment.newInstance()
                }
            }
            return RecentlyAddedFragment.newInstance()
        }

        override fun getCount(): Int {
            return 3
        }
    }

    override fun onResume() {
        super.onResume()
        findContent()
    }

    private fun findContent() {
        showProgress()
        ContentService.findContent(UserContentDTO(UserInfo.userId)).applySchedulers()
                .subscribe(
                        { content ->
                            closeProgress()
                            ContentContainer.initContent(content)
                            container.adapter = mSectionsPagerAdapter
                        },
                        { error ->
                            closeProgress()
                            handleException(error)
                        }
                )
    }

    private fun logoutConfirmation() {
        alert(String.format(getString(R.string.message_confirmation_logout)), getString(R.string.title_logout)) {
            positiveButton(R.string.yes) { logout() }
            negativeButton(R.string.no) {}
        }.show()
    }

    private fun logout() {
        UserInfo.clearData(applicationContext)
        startActivity<SplashActivity>()
    }

}

