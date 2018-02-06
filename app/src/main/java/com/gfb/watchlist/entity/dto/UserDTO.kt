package com.gfb.watchlist.entity.dto

import com.gfb.watchlist.entity.Content

/**
 * Created by Gustavo on 18/01/2018.
 */
class UserDTO(var email: String?,
              var password: String?,
              var watchList: List<Content>?,
              var archive: List<Content>?) {


    constructor(email: String, password: String) : this(email, password, null, null)

    constructor(email: String?) : this(email, null, mutableListOf(), mutableListOf())
}