package com.gfb.watchlist.fragment


import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.gfb.watchlist.R
import com.gfb.watchlist.adapter.ContentAdapter
import com.gfb.watchlist.entity.Content
import com.gfb.watchlist.entity.UserInfo
import com.gfb.watchlist.service.ContentService
import org.jetbrains.anko.toast


/**
 * A simple [Fragment] subclass.
 */
class RecentlyAddedFragment : BaseFragment() {
    private lateinit var recyclerViewContent: RecyclerView

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        var view = inflater!!.inflate(R.layout.fragment_recently_added, container, false)
        recyclerViewContent = view.findViewById(R.id.recyclerViewContent)
        recyclerViewContent.layoutManager = LinearLayoutManager(view.context)
        return view

    }

    companion object {

        fun newInstance(): RecentlyAddedFragment {
            return RecentlyAddedFragment()
        }
    }

    override fun onStart() {
        super.onStart()
        ContentService.findContent(UserInfo.userId).applySchedulers()
                .subscribe(
                        {
                            content ->
                            closeProgress()
                            setAdapter(content)
                        },
                        {
                            error ->
                            closeProgress()
                            handleException(error)
                        }
                )
    }

    fun setAdapter(content: List<Content>) {
        val adapter = ContentAdapter(content) {
            toast("${it.title} selected")
        }
        recyclerViewContent.adapter = adapter
    }
}
