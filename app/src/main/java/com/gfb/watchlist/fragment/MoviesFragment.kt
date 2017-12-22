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
import org.jetbrains.anko.support.v4.toast


class MoviesFragment : Fragment() {

    private lateinit var movieList:RecyclerView

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        var view = inflater!!.inflate(R.layout.fragment_movies, container, false)
        movieList = view.findViewById(R.id.movieList)
        movieList.layoutManager = LinearLayoutManager(view.context)
        return view

    }

        companion object {

        fun newInstance(): MoviesFragment {
            return MoviesFragment()
        }
    }

    override fun onStart() {
        super.onStart()
        val movies = listOf(Content("Home Alone", "1968"), Content("Castaway","1998"))
        val adapter = ContentAdapter(movies){
            toast("${it.title} selected")
        }
        movieList.adapter = adapter

    }
}
