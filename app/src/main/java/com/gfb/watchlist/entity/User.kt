package com.gfb.watchlist.entity

import java.io.Serializable

/**
 * Created by Gustavo on 12/26/2017.
 */
class User(
        var id: String,
        var email: String,
        var password: String,
        var status: Status,
        var watchList: List<Content>,
        var archive: List<Content>): Serializable{

    enum class Status {
        PENDING,
        PENDING_RESET,
        ACTIVE
    }
}





