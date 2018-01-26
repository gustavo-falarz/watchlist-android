package com.gfb.watchlist.entity

import android.content.Context
import com.chibatching.kotpref.KotprefModel
import com.chibatching.kotpref.bulk
import com.firebase.ui.auth.AuthUI


/**
 * Created by Gustavo on 12/26/2017.
 */
object UserInfo : KotprefModel() {

    var userId by stringPref()
    var email by stringPref()

    fun saveUserLocally(user: User) {
        UserInfo.userId = user.id
        UserInfo.email = user.email
    }

    fun clearData(context: Context) {
        AuthUI.getInstance()
                .signOut(context)
                .addOnCompleteListener {
                }

        UserInfo.bulk {
            userId = ""
            email = ""
        }
    }

}