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
import com.gfb.watchlist.service.ContentService
import com.gfb.watchlist.util.Constants
import com.gfb.watchlist.util.ImageUtil.load
import kotlinx.android.synthetic.main.activity_content_details.*
import org.jetbrains.anko.*

class PreviewActivity : BaseActivity() {
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
        fab.setOnClickListener { confirmationAdd() }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_preview, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_add_content -> confirmationAdd()
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun getContent() {
        showProgress()
        ContentService.getContent(contentId).applySchedulers()
                .subscribe(
                        {
                            closeProgress()
                            content = it
                            populate()
                        },
                        {
                            closeProgress()
                            handleException(it)
                        }
                )
    }

    private fun confirmationAdd(): Boolean {
        showProgress()
        ContentService.addContent(UserContentDTO(WatchlistApplication.prefs.userId, content, null)).applySchedulers()
                .subscribe(
                        { response ->
                            if (response.status) {
                                alert(response.message, getString(R.string.title_success)) {
                                    yesButton {
                                        startActivity(intentFor<MainActivity>().clearTask().newTask())
                                        ContentContainer.updated = true
                                        finish()
                                    }
                                }.show()
                            }
                            closeProgress()
                        },
                        {
                            closeProgress()
                            handleException(it)
                        }
                )

        return true
    }
}
