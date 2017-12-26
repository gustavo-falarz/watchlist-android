package com.gfb.watchlist.activity

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import com.gfb.watchlist.R
import com.gfb.watchlist.adapter.ContentAdapter
import com.gfb.watchlist.entity.Content
import com.gfb.watchlist.service.MovieService
import kotlinx.android.synthetic.main.activity_add_to_list.*
import org.jetbrains.anko.toast

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
        MovieService.searchOnImdb(query).applySchedulers()
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

    fun setAdapter(contents: List<Content>) {
        recyclerView.adapter = ContentAdapter(contents) {
            toast("${it.title} selected")
        }
    }
}


