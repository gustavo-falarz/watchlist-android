package com.gfb.watchlist.activity

import android.support.v7.app.AppCompatActivity
import com.gfb.watchlist.R
import io.reactivex.Observable

import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

/**
 * Created by Gustavo on 12/4/2017.
 */

open class BaseActivity : AppCompatActivity() {


    fun <T> Observable<T>.applySchedulers(): Observable<T> {
        return subscribeOn(Schedulers.computation()).observeOn(AndroidSchedulers.mainThread())
    }
}