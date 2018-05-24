package com.gfb.watchlist.activity

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import com.gfb.watchlist.R
import com.gfb.watchlist.WatchlistApplication
import com.gfb.watchlist.entity.Content
import com.gfb.watchlist.entity.ContentContainer
import com.gfb.watchlist.entity.UserInfo
import com.gfb.watchlist.entity.dto.UserContentDTO
import com.gfb.watchlist.prefs
import com.gfb.watchlist.service.ContentService
import com.gfb.watchlist.util.Constants
import com.gfb.watchlist.util.ImageUtil.load
import kotlinx.android.synthetic.main.activity_content_details.*
import org.jetbrains.anko.alert
import org.jetbrains.anko.share
import org.jetbrains.anko.yesButton

class ContentDetailsActivity : BaseActivity() {
    lateinit var content: Content

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_content_details)
        setupToolbar(R.string.title_details)
        setupActionBar()

        content = intent.getSerializableExtra(Constants.TRANSITION_KEY_CONTENT) as Content

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
        fab.setOnClickListener { confirmationArchive() }

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
        when {
            share("${Constants.URL_IMDB}${content.imdbID}") ->
                closeProgress()
        }
        return true
    }

    private fun confirmationArchive(): Boolean {
        alert(String.format(getString(R.string.message_confirmation_archive_content), content.title), getString(R.string.title_archive_content)) {
            positiveButton(R.string.yes) { archiveContent(content) }
            negativeButton(R.string.no) {}
        }.show()
        return true
    }

    private fun confirmationDelete(): Boolean {
        alert(String.format(getString(R.string.message_confirmation_delete_content), content.title), getString(R.string.title_delete_content)) {
            positiveButton(R.string.yes) { deleteContent(content) }
            negativeButton(R.string.no) {}
        }.show()
        return true
    }

    private fun archiveContent(content: Content) {
        showProgress()
        ContentService.archiveContent(UserContentDTO(prefs.userId, content)).applySchedulers()
                .subscribe(
                        { response ->
                            closeProgress()
                            alert(response.message, getString(R.string.title_success)) {
                                yesButton {
                                    ContentContainer.updated = true
                                    finish()
                                }
                            }.show()
                        },
                        { error ->
                            closeProgress()
                            handleException(error)
                        }
                )
    }

    private fun deleteContent(content: Content) {
        showProgress()
        ContentService.deleteContent(UserContentDTO(prefs.userId, content)).applySchedulers()
                .subscribe(
                        { response ->
                            closeProgress()
                            alert(response.message, getString(R.string.title_success)) {
                                yesButton {
                                    ContentContainer.updated = true
                                    finish()
                                }
                            }.show()
                        },
                        { error ->
                            closeProgress()
                            handleException(error)
                        }
                )
    }
}
