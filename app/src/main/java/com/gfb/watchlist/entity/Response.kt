package com.gfb.watchlist.entity

/**
 * Created by Gustavo on 12/27/2017.
 */
data class Response <out T>(
        val status: Boolean,
        val data: T?,
        val message: String?
)