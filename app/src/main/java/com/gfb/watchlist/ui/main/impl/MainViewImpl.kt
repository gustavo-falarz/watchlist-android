package com.gfb.watchlist.ui.main.impl

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
import android.view.View
import android.widget.TextView
import com.gfb.watchlist.R
import com.gfb.watchlist.entity.Content
import com.gfb.watchlist.entity.ContentContainer
import com.gfb.watchlist.prefs
import com.gfb.watchlist.ui.BaseView
import com.gfb.watchlist.ui.addToList.impl.AddToListViewImpl
import com.gfb.watchlist.ui.archive.impl.ArchiveViewImpl
import com.gfb.watchlist.ui.changePassword.impl.ChangePasswordViewImpl
import com.gfb.watchlist.ui.content.impl.MoviesViewImpl
import com.gfb.watchlist.ui.content.impl.RecentlyAddedViewImpl
import com.gfb.watchlist.ui.content.impl.SeriesViewImpl
import com.gfb.watchlist.ui.main.MainView
import com.gfb.watchlist.ui.splash.impl.SplashViewImpl
import com.gfb.watchlist.util.Constants.TRANSITION_KEY_CONTENT
import io.reactivex.Observable
import io.reactivex.rxkotlin.subscribeBy
import kotlinx.android.synthetic.main.app_bar_main.*
import kotlinx.android.synthetic.main.content_main.*
import org.jetbrains.anko.*


@Suppress("DEPRECATION")
class MainViewImpl : BaseView(), NavigationView.OnNavigationItemSelectedListener, MainView {
    private val presenter = MainPresenterImpl(this, this)
    private var mSectionsPagerAdapter: MainViewImpl.SectionsPagerAdapter? = null

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
        textUser.text = prefs.userEmail

        fab.setOnClickListener { startActivity<AddToListViewImpl>() }
        when {
            prefs.googleSignIn -> hideForgotPass()
        }
    }

    override fun onBackPressed() {
        val drawer = findViewById<DrawerLayout>(R.id.drawer_layout)
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START)
        } else {
            moveTaskToBack(true)
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        when (id) {
            R.id.nav_add_content -> {
                startActivity<AddToListViewImpl>()
            }
            R.id.nav_archive -> {
                startActivity<ArchiveViewImpl>()
            }
            R.id.nav_password -> {
                startActivity<ChangePasswordViewImpl>(TRANSITION_KEY_CONTENT to prefs.userEmail)
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
                    return RecentlyAddedViewImpl.newInstance()
                }
                1 -> {
                    return MoviesViewImpl.newInstance()
                }
                2 -> {
                    return SeriesViewImpl.newInstance()
                }
            }
            return RecentlyAddedViewImpl.newInstance()
        }

        override fun getCount(): Int {
            return 3
        }
    }

    override fun onResume() {
        super.onResume()
        when {
            ContentContainer.updated -> findContent()
        }
    }

    private fun findContent() {
        presenter.getContent()
    }

    private fun logoutConfirmation() {
        alert(String.format(getString(R.string.message_confirmation_logout)), getString(R.string.title_logout)) {
            positiveButton(R.string.yes) { logout() }
            negativeButton(R.string.no) {}
        }.show()
    }

    private fun logout() {
        presenter.logout()
    }

    private fun hideForgotPass() {
        val navigationView = findViewById<View>(R.id.nav_view) as NavigationView
        val navMenu = navigationView.menu
        navMenu.findItem(R.id.nav_password).isVisible = false
    }

    override fun onDestroy() {
        super.onDestroy()
        ContentContainer.updated = true
    }

    override fun onGetContent(observable: Observable<MutableList<Content>>) {
        showProgress()
        observable.applySchedulers()
                .subscribeBy(
                        onNext = { content ->
                            presenter.onContentLoaded(content)
                        },
                        onError = { error ->
                            closeProgress()
                            handleException(error)
                        },
                        onComplete = {
                            closeProgress()
                        }
                )
    }

    override fun onContentLoaded() {
        container.adapter = mSectionsPagerAdapter
    }

    override fun onLogout() {
        startActivity(intentFor<SplashViewImpl>().clearTask().newTask())
    }

}

