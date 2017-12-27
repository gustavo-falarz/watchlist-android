package com.gfb.watchlist.fragment

import android.app.ProgressDialog
import android.support.v4.app.Fragment
import com.gfb.watchlist.R
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import org.jetbrains.anko.support.v4.alert
import org.jetbrains.anko.support.v4.indeterminateProgressDialog
import org.jetbrains.anko.yesButton

/**
 * Created by Gustavo on 12/26/2017.
 */
open class BaseFragment: Fragment(){

    lateinit var progress: ProgressDialog


    fun <T> Observable<T>.applySchedulers(): Observable<T> {
        return subscribeOn(Schedulers.computation()).observeOn(AndroidSchedulers.mainThread())
    }

    fun showProgress() {
        progress = indeterminateProgressDialog(message = "Please wait a bit…", title = "Fetching data")
    }

    fun closeProgress() {
        progress.hide()
    }

    fun handleException(exception: Throwable) {
        exception.message?.let { alert(it, getString(R.string.error_title)) { yesButton { } }.show() }
    }

    fun showWarning(message: String){
        alert (message, getString(R.string.error_title)){yesButton {  }}
    }
}