package com.gfb.watchlist.fragment


import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.gfb.watchlist.R
import com.gfb.watchlist.adapter.ContentAdapter
import com.gfb.watchlist.entity.Content
import com.gfb.watchlist.entity.ContentContainer
import com.gfb.watchlist.entity.UserInfo
import com.gfb.watchlist.entity.dto.UserContentDTO
import com.gfb.watchlist.service.ContentService
import org.jetbrains.anko.support.v4.toast


class MoviesFragment : BaseFragment() {

    private lateinit var recyclerViewContent: RecyclerView

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        var view = inflater!!.inflate(R.layout.fragment_movies, container, false)
        recyclerViewContent = view.findViewById(R.id.recyclerViewContent)
        recyclerViewContent.layoutManager = LinearLayoutManager(view.context)
        return view

    }

    companion object {
        fun newInstance(): MoviesFragment {
            return MoviesFragment()
        }
    }

    override fun onStart() {
        super.onStart()
        when {
            ContentContainer.isEmpty() -> findContent()
            else -> setAdapter()
        }
    }

    private fun setAdapter() {
        val adapter = ContentAdapter(ContentContainer.getContent(getString(R.string.movies))) {
            toast("${it.title} selected")
        }
        recyclerViewContent.adapter = adapter
    }

    private fun findContent() {
        showProgress()
        ContentService.findContent(UserContentDTO(UserInfo.userId, null, null)).applySchedulers()
                .subscribe(
                        { content ->
                            closeProgress()
                            ContentContainer.initContent(content)
                            setAdapter()
                        },
                        { error ->
                            closeProgress()
                            handleException(error)
                        }
                )
    }

}
