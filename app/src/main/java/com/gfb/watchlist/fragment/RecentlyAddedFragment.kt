package com.gfb.watchlist.fragment


import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.gfb.watchlist.R


/**
 * A simple [Fragment] subclass.
 */
class RecentlyAddedFragment : Fragment() {


    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater!!.inflate(R.layout.fragment_recently_added, container, false)
    }

    companion object {

        fun newInstance(): RecentlyAddedFragment {
            return RecentlyAddedFragment()
        }
    }
}
