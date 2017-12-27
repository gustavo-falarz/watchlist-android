package com.gfb.watchlist.fragment


import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.gfb.watchlist.R


/**
 * Created by @author Gustavo
 */

class SeriesFragment : BaseFragment() {

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater!!.inflate(R.layout.fragment_series, container, false)
    }

    companion object {

        fun newInstance(): SeriesFragment {
            return SeriesFragment()
        }

    }

}
