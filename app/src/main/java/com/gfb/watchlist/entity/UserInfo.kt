package com.gfb.watchlist.entity

import com.chibatching.kotpref.KotprefModel
import com.chibatching.kotpref.bulk

/**
* Created by Gustavo on 12/26/2017.
*/
object UserInfo: KotprefModel() {

    var userId by stringPref()
    var email by stringPref()


    fun clearData(){
        UserInfo.bulk {
            userId = ""
            email = ""
        }
    }

}