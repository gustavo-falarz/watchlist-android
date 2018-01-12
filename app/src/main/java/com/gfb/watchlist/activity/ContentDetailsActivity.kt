package com.gfb.watchlist.activity

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import com.gfb.watchlist.R
import com.gfb.watchlist.entity.Content
import com.gfb.watchlist.entity.ContentContainer
import com.gfb.watchlist.entity.UserInfo
import com.gfb.watchlist.entity.dto.UserContentDTO
import com.gfb.watchlist.service.ContentService
import com.gfb.watchlist.util.ImageUtil.load
import kotlinx.android.synthetic.main.activity_content_details.*
import org.jetbrains.anko.alert
import org.jetbrains.anko.yesButton

class ContentDetailsActivity : BaseActivity() {
    lateinit var content: Content

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_content_details)
        setupToolbar(R.string.title_details)
        setupActionBar()

        content = intent.getSerializableExtra("content") as Content

        tvTitle.text = content.title
        tvYear.text = content.year
        tvDirector.text = content.director
        tvGenre.text = content.genre
        tvPlot.text = content.plot
        tvRuntime.text = content.runtime
        tvProduction.text = content.production
        tvReleased.text = content.released
        tvActors.text = content.actors
        imPoster.load(content.poster!!) { request -> request.fit() }
        fab.setOnClickListener { confirmationArchive() }

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_settings ->
                confirmationArchive()
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun confirmationArchive(): Boolean {
        alert(String.format(getString(R.string.message_confirmation_archive_content), content.title), getString(R.string.title_add_content)) {
            positiveButton(R.string.yes) { archiveContent(content) }
            negativeButton(R.string.no) {}
        }.show()
        return true
    }

    private fun archiveContent(content: Content) {
        showProgress()
        ContentService.archiveContent(UserContentDTO(UserInfo.userId, content, null)).applySchedulers()
                .subscribe(
                        { response ->
                            closeProgress()
                            alert(response.message, getString(R.string.title_success)) {
                                yesButton {
                                    ContentContainer.content?.remove(content)
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
