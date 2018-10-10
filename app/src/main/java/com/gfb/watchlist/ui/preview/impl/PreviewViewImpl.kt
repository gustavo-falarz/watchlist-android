package com.gfb.watchlist.ui.preview.impl

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import com.gfb.watchlist.R
import com.gfb.watchlist.activity.BaseActivity
import com.gfb.watchlist.activity.MainActivity
import com.gfb.watchlist.entity.Content
import com.gfb.watchlist.entity.ContentContainer
import com.gfb.watchlist.entity.Result
import com.gfb.watchlist.prefs
import com.gfb.watchlist.ui.preview.PreviewView
import com.gfb.watchlist.util.Constants
import com.gfb.watchlist.util.ImageUtil.load
import io.reactivex.rxkotlin.subscribeBy
import kotlinx.android.synthetic.main.activity_content_details.*
import org.jetbrains.anko.*

class PreviewViewImpl : BaseActivity(), PreviewView {
    private lateinit var contentId: String
    private lateinit var content: Content

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_preview)
        setupToolbar(R.string.title_details)
        setupActionBar()

        contentId = intent.getStringExtra(Constants.TRANSITION_KEY_CONTENT) as String
        getContent()
    }

    private fun populate() {
        tvTitle.text = content.title
        tvYear.text = content.year
        tvDirector.text = content.director
        tvGenre.text = content.genre
        tvPlot.text = content.plot
        tvRuntime.text = content.runtime
        tvProduction.text = content.production
        tvReleased.text = content.released
        tvActors.text = content.actors
        imPoster.load(content.poster) { request -> request.fit() }
        fab.setOnClickListener { confirmAddition() }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_preview, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_add_content ->
                confirmAddition()
            R.id.action_share_content ->
                shareContent()
            else ->
                super.onOptionsItemSelected(item)
        }
    }

    private fun shareContent(): Boolean {
        when {
            share("${Constants.URL_IMDB}${content.imdbID}") ->
                closeProgress()
        }
        return true
    }

    override fun getContent() {
        showProgress()
        presenter.getContent(contentId).applySchedulers()
                .subscribeBy(
                        onNext = {
                            content = it
                            populate()
                        },
                        onError = {
                            closeProgress()
                            handleException(it)
                        },
                        onComplete = {
                            closeProgress()
                        }
                )
    }

    private fun confirmAddition(): Boolean {
        alert(String.format(getString(R.string.message_confirmation_add_content), content.title),
                getString(R.string.title_add_content)) {
            positiveButton(R.string.yes) { addToList() }
            negativeButton(R.string.no) {}
        }.show()
        return true
    }

    private val presenter = PreviewPresenterImpl()

    override fun addToList() {
        showProgress()
        presenter.addToList(prefs.userId, content).applySchedulers()
                .subscribeBy(
                        onNext = {
                            handleResult(it)
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

    private fun handleResult(it: Result) {
        if (it.status) {
            alert(it.message, getString(R.string.title_success)) {
                yesButton {
                    startActivity(intentFor<MainActivity>().clearTask().newTask())
                    ContentContainer.updated = true
                    finish()
                }
            }.show()
        }
    }
}
