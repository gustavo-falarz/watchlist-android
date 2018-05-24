package com.gfb.watchlist.entity

import android.content.Context
import android.content.SharedPreferences
import com.firebase.ui.auth.AuthUI
import com.gfb.watchlist.WatchlistApplication
import com.gfb.watchlist.prefs


/**
 * Created by Gustavo on 12/26/2017.
 */
class UserInfo(context: Context) {
    companion object {
        fun saveUserLocally(user: User, google:Boolean) {
            prefs.userEmail = user.email
            prefs.userId = user.id
            prefs.googleSignIn = google
        }

        fun clearData(context: Context) {
            AuthUI.getInstance()
                    .signOut(context)
                    .addOnCompleteListener {
                    }
            prefs.userEmail = ""
            prefs.userId = ""
            prefs.googleSignIn = false
        }
    }

    private val PREF_KEY = "com.gfb.watchlist"
    private val PREF_ID = "ID"
    private val PREF_EMAIL = "EMAIL"
    private val PREF_GOOGLE = "GOOGLE"
    private val prefs: SharedPreferences = context.getSharedPreferences(PREF_KEY, 0)

    var userId: String
        get() = prefs.getString(PREF_ID, "")
        set(value) = prefs.edit().putString(PREF_ID, value).apply()

    var userEmail: String
        get() = prefs.getString(PREF_EMAIL, "")
        set(value) = prefs.edit().putString(PREF_EMAIL, value).apply()

    var googleSignIn: Boolean
        get() = prefs.getBoolean(PREF_GOOGLE, false)
        set(value) = prefs.edit().putBoolean(PREF_GOOGLE, value).apply()

}