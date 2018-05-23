package com.gfb.watchlist.activity

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.Menu
import android.view.MenuItem
import com.gfb.watchlist.R
import com.gfb.watchlist.WatchlistApplication
import com.gfb.watchlist.adapter.ArchiveAdapter
import com.gfb.watchlist.entity.Content
import com.gfb.watchlist.entity.UserInfo
import com.gfb.watchlist.service.ContentService
import kotlinx.android.synthetic.main.activity_archive.*
import org.jetbrains.anko.alert

class ArchiveActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_archive)
        setupToolbar(R.string.title_archive)
        setupActionBar()

        recyclerViewContent.layoutManager = LinearLayoutManager(this)

    }


    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_archive, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_clear_archive ->
                confirmationDelete()
            else -> super.onOptionsItemSelected(item)
        }
    }


    private fun confirmationDelete(): Boolean {
        alert(getString(R.string.message_clear_archive), getString(R.string.title_clear_archive)) {
            positiveButton(R.string.yes) { clearArchive() }
            negativeButton(R.string.no) {}
        }.show()
        return true
    }

    override fun onStart() {
        super.onStart()
        findArchive()
    }

    private fun findArchive() {
        showProgress()
        ContentService.findArchive(WatchlistApplication.prefs.userId).applySchedulers()
                .subscribe(
                        { content ->
                            closeProgress()
                            setAdapter(content)
                        },
                        { error ->
                            closeProgress()
                            handleException(error)
                        }
                )
    }

    private fun setAdapter(contents: List<Content>) {
        val adapter = ArchiveAdapter(contents) {}
        recyclerViewContent.adapter = adapter
    }

    private fun clearArchive() {
        showProgress()
        ContentService.clearArchive(WatchlistApplication.prefs.userId).applySchedulers()
                .subscribe(
                        { response ->
                            closeProgress()
                            setAdapter(mutableListOf())
                           showMessage(response.message)
                        },
                        { error ->
                            closeProgress()
                            handleException(error)
                        }
                )
    }
}
