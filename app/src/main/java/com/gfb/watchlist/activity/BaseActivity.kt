package com.gfb.watchlist.activity

import android.os.HandlerThread
import android.os.Looper
import android.os.Process
import android.support.v7.app.AppCompatActivity

import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

/**
 * Created by Gustavo on 12/4/2017.
 */

open class BaseActivity : AppCompatActivity() {


    fun prepare(observable: Observable<*>): Observable<*> {
        return observable
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
    }

}