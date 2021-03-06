package com.gfb.watchlist.ui

import android.support.v4.app.Fragment
import com.crashlytics.android.Crashlytics
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
        try {
            if (progress == null) {
                progress = Progress(context!!)
            }
            progress!!.show()
        } catch (e: Exception) {
            Crashlytics.logException(e)
        }
    }

    protected fun closeProgress() {
        try {
            if (progress != null && progress!!.isShowing) {
                progress!!.dismiss()
            }
        } catch (e: Exception) {
            Crashlytics.logException(e)
        }
    }

    @Suppress("NestedLambdaShadowedImplicitParameter")
    fun handleException(exception: Throwable) {
        exception.message?.let { alert(it, getString(R.string.error_title)) { yesButton { } }.show() }
    }

    @Suppress("unused")
    fun showWarning(message: String) {
        alert(message, getString(R.string.error_title)) { yesButton { } }
    }

}