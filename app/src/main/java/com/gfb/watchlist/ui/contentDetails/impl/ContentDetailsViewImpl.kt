package com.gfb.watchlist.ui.contentDetails.impl

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import com.gfb.watchlist.R
import com.gfb.watchlist.ui.BaseView
import com.gfb.watchlist.entity.Content
import com.gfb.watchlist.entity.ContentContainer
import com.gfb.watchlist.entity.Result
import com.gfb.watchlist.prefs
import com.gfb.watchlist.ui.contentDetails.ContentDetailsView
import com.gfb.watchlist.util.Constants
import com.gfb.watchlist.util.ImageUtil.load
import io.reactivex.Observable
import io.reactivex.rxkotlin.subscribeBy
import kotlinx.android.synthetic.main.activity_content_details.*
import org.jetbrains.anko.alert
import org.jetbrains.anko.share
import org.jetbrains.anko.yesButton

class ContentDetailsViewImpl : BaseView(), ContentDetailsView {
    val presenter = ContentDetailsPresenterImpl(this)

    lateinit var content: Content

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_content_details)
        (R.string.title_details)
        setupActionBar()

        content = intent.getSerializableExtra(Constants.TRANSITION_KEY_CONTENT) as Content
        fab.setOnClickListener { confirmationArchive() }
        presenter.populateFields()
    }

    override fun populateFields() {
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
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_content, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_mark_watched ->
                confirmationArchive()
            R.id.action_delete_content ->
                confirmationDelete()
            R.id.action_share_content ->
                shareContent()
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun shareContent(): Boolean {
        presenter.shareContent(content.imdbID)
        return true
    }

    private fun confirmationArchive(): Boolean {
        alert(String.format(getString(R.string.message_confirmation_archive_content),
                content.title), getString(R.string.title_archive_content)) {
            positiveButton(R.string.yes) { archiveContent(content) }
            negativeButton(R.string.no) {}
        }.show()
        return true
    }

    private fun confirmationDelete(): Boolean {
        alert(String.format(getString(R.string.message_confirmation_delete_content),
                content.title), getString(R.string.title_delete_content)) {
            positiveButton(R.string.yes) { deleteContent(content) }
            negativeButton(R.string.no) {}
        }.show()
        return true
    }

    private fun archiveContent(content: Content) {
        presenter.deleteContent(prefs.userId, content)
    }

    private fun deleteContent(content: Content) {
        presenter.deleteContent(prefs.userId, content)
    }

    override fun onArchiveContent(observable: Observable<Result>) {
        showProgress()
        observable.applySchedulers()
                .subscribeBy(
                        onNext = {
                            presenter.onContentArchived(it)
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

    override fun onDeleteContent(observable: Observable<Result>) {
        showProgress()
        observable.applySchedulers()
                .subscribeBy(
                        onNext = {
                            presenter.onContentDeleted(it)
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

    override fun onContentArchived(result: Result) {
        alert(result.message, getString(R.string.title_success)) {
            yesButton {
                ContentContainer.updated = true
                finish()
            }
        }.show()
    }

    override fun onContentDeleted(result: Result) {
        alert(result.message, getString(R.string.title_success)) {
            yesButton {
                ContentContainer.updated = true
                finish()
            }
        }.show()
    }

    override fun shareContent(imdbUrl: String, imdbId: String) {
        share("$imdbUrl$imdbId")
    }
}
