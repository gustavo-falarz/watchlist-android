package com.gfb.watchlist.ui.content.impl


import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.gfb.watchlist.R
import com.gfb.watchlist.ui.content.ContentFragment
import com.gfb.watchlist.ui.content.ContentView
import com.gfb.watchlist.util.Constants


class MoviesViewImpl : ContentFragment(), ContentView {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_movies, container, false)
        recyclerView = view.findViewById(R.id.recyclerViewMovies)
        etSearch = view.findViewById(R.id.etSearch)
        recyclerView.layoutManager = LinearLayoutManager(view.context)
        etSearch.addTextChangedListener(onSearch())
        type = Constants.TYPE_MOVIE
        setupAdapter()
        return view
    }

    companion object {
        fun newInstance(): MoviesViewImpl {
            return MoviesViewImpl()
        }
    }

}
