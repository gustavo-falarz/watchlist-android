package com.gfb.watchlist.ui.archive.impl

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.Menu
import android.view.MenuItem
import com.gfb.watchlist.R
import com.gfb.watchlist.ui.BaseView
import com.gfb.watchlist.ui.adapter.ArchiveAdapter
import com.gfb.watchlist.entity.Content
import com.gfb.watchlist.entity.Result
import com.gfb.watchlist.prefs
import com.gfb.watchlist.ui.archive.ArchiveView
import io.reactivex.Observable
import io.reactivex.rxkotlin.subscribeBy
import kotlinx.android.synthetic.main.activity_archive.*
import org.jetbrains.anko.alert

class ArchiveViewImpl : BaseView(), ArchiveView {

    private val presenter = ArchivePresenterImpl(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_archive)
        (R.string.title_archive)
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

    private fun createAdapter(contents: List<Content>) {
        val adapter = ArchiveAdapter(contents) {}
        recyclerViewContent.adapter = adapter
    }

    private fun confirmationDelete(): Boolean {
        alert(getString(R.string.message_clear_archive), getString(R.string.title_clear_archive)) {
            positiveButton(R.string.yes) { presenter.clearArchive(prefs.userId) }
            negativeButton(R.string.no) {}
        }.show()
        return true
    }

    override fun onStart() {
        super.onStart()
        getArchive()
    }

    private fun getArchive() {
        presenter.getArchive(prefs.userId)
    }

    override fun onGetArchive(observable: Observable<List<Content>>) {
        showProgress()
        observable.applySchedulers()
                .subscribeBy(
                        onNext = {
                            createAdapter(it)
                        },
                        onError = {
                            handleException(it)
                            closeProgress()
                        },
                        onComplete = {
                            closeProgress()
                        }
                )
    }

    override fun onClearArchive(observable: Observable<Result>) {
        showProgress()
        observable.applySchedulers()
                .subscribeBy(
                        onNext = {
                            createAdapter(mutableListOf())
                            showMessage(it.message)
                        },
                        onError = {
                            handleException(it)
                            closeProgress()
                        },
                        onComplete = {
                            closeProgress()
                        }
                )
    }
}
