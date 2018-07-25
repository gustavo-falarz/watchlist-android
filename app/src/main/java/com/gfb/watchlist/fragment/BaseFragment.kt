package com.gfb.watchlist.fragment

import android.support.v4.app.Fragment
import com.gfb.watchlist.R
import com.gfb.watchlist.widget.Progress
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import org.jetbrains.anko.support.v4.alert
import org.jetbrains.anko.yesButton

/**
 * Created by Gustavo on 12/26/2017.
 */
open class BaseFragment : Fragment() {

    private var progress: Progress? = null


    fun <T> Observable<T>.applySchedulers(): Observable<T> {
        return subscribeOn(Schedulers.computation()).observeOn(AndroidSchedulers.mainThread())
    }

    protected fun showProgress() {
        if (progress == null) {
            progress = Progress(context)
        }
        progress!!.show()
    }

    protected fun closeProgress() {
        if (progress != null && progress!!.isShowing) {
            progress!!.dismiss()
        }
    }

    fun handleException(exception: Throwable) {
        exception.message?.let { alert(it, getString(R.string.error_title)) { yesButton { } }.show() }
    }

    fun showWarning(message: String) {
        alert(message, getString(R.string.error_title)) { yesButton { } }
    }

}