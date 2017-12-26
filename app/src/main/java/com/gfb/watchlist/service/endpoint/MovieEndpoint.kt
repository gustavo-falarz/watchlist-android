package com.gfb.watchlist.service.endpoint

import com.gfb.watchlist.entity.Content

import io.reactivex.Observable
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

/**
 * Created by Gustavo on 10/26/2017.
 */

interface MovieEndpoint {

    @POST("content/addMovie")
    fun addMovie(@Body content: Content): Observable<Content>

    @GET("content/searchOnImdb/{param}")
    fun searchOnImdb(@Path("param") param: String): Observable<List<Content>>

}
