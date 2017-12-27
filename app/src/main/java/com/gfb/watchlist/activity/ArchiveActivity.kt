package com.gfb.watchlist.activity

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import com.gfb.watchlist.R
import com.gfb.watchlist.adapter.ArchiveAdapter
import com.gfb.watchlist.entity.Content
import com.gfb.watchlist.entity.UserInfo
import com.gfb.watchlist.service.ContentService
import kotlinx.android.synthetic.main.activity_archive.*
import org.jetbrains.anko.toast

class ArchiveActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_archive)
        setupToolbar(R.string.title_archive)
        setupActionBar()

        recyclerViewContent.layoutManager = LinearLayoutManager(this)

    }

    override fun onStart() {
        super.onStart()
        findArchive()
    }

    private fun findArchive(){
        showProgress()
        ContentService.findArchive(UserInfo.userId).applySchedulers()
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

    private fun setAdapter(content: List<Content>) {
        val adapter = ArchiveAdapter(content) {
            toast("${it.title} selected")
        }
        recyclerViewContent.adapter = adapter
    }
}
