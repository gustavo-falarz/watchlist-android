package com.gfb.watchlist.service.endpoint

import com.gfb.watchlist.entity.Content
import com.gfb.watchlist.entity.Result
import com.gfb.watchlist.entity.dto.UserContentDTO

import io.reactivex.Observable
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

/**
 * Created by Gustavo on 10/26/2017.
 */

interface MovieEndpoint {

    @POST("content/add")
    fun addContent(@Body dto: UserContentDTO): Observable<Result>

    @POST("content/archive")
    fun archiveContent(@Body dto: UserContentDTO): Observable<Result>

    @GET("content/searchOnImdb/{param}")
    fun searchOnImdb(@Path("param") param: String): Observable<List<Content>>

    @GET("content/findArchive/{userId}")
    fun findArchive(@Path("userId") param: String): Observable<List<Content>>

    @POST("content/findWithParameters")
    fun findContent(@Body dto: UserContentDTO): Observable<MutableList<Content>>


    /**
     * Funcões pra aula
     */

    @POST("content/addContent")
    fun addContentAula(@Body content: Content): Observable<Result>

    @GET("content/findAllByTitle/{param}")
    fun searchAula(@Path("param") param: String): Observable<List<Content>>

}
