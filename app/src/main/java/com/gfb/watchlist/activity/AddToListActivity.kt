package com.gfb.watchlist.activity

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import com.gfb.watchlist.R
import com.gfb.watchlist.adapter.ContentAdapter
import com.gfb.watchlist.entity.Content
import com.gfb.watchlist.entity.dto.UserContentDTO
import com.gfb.watchlist.entity.UserInfo
import com.gfb.watchlist.service.ContentService
import kotlinx.android.synthetic.main.activity_add_to_list.*
import org.jetbrains.anko.alert
import org.jetbrains.anko.noButton
import org.jetbrains.anko.yesButton

class AddToListActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_to_list)
        setupToolbar(R.string.search_menu_title)
        setuActionBar()

        recyclerView.layoutManager = LinearLayoutManager(this)
        imSearchOnImdb.setOnClickListener({ searchOnImdb() })
    }


    fun searchOnImdb() {
        val query = etSearchContent.text.toString()
        showProgress()
        ContentService.searchOnImdb(query).applySchedulers()
                .subscribe(
                        { contents ->
                            setAdapter(contents)
                            closeProgress()
                        },
                        { error ->
                            handleException(error)
                            closeProgress()
                        }
                )

    }

    private fun setAdapter(contents: List<Content>) {
        recyclerView.adapter = ContentAdapter(contents) {
            confirmAddition(it)
        }
    }

    private fun confirmAddition(content: Content) {
        alert("Add ${content.title} to your watchlist?", "Add content") {
            yesButton { addToList(content) }
            noButton {}
        }.show()
    }

    private fun addToList(content: Content) {
        showProgress()
        ContentService.addContent(UserContentDTO(UserInfo.userId, content, null)).applySchedulers()
                .subscribe(
                        { response ->
                            if (response.isStatus) {
                                finish()
                            }
                            closeProgress()
                        },
                        { error ->
                            closeProgress()
                            handleException(error)
                        }
                )
    }

}


