package com.gfb.watchlist.service.endpoint

import com.gfb.watchlist.entity.Content

import io.reactivex.Observable
import retrofit2.http.Body
import retrofit2.http.POST

/**
 * Created by Gustavo on 10/26/2017.
 */

interface MovieEndpoint{

    @POST("customer/addMovie")
    fun addMovie(@Body content: Content): Observable<Content>

}
