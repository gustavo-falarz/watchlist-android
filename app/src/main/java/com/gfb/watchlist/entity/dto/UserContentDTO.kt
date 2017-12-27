package com.gfb.watchlist.entity.dto

import com.gfb.watchlist.entity.Content


class UserContentDTO(var user: String?,
                     var content: Content?,
                     var type: Type?) {
    enum class Type {
        MOVIE,
        SERIES
    }
}