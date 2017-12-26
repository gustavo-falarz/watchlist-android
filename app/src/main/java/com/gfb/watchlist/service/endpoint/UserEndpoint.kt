package com.gfb.watchlist.service.endpoint

import com.gfb.watchlist.entity.User
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Path


/**
 * Created by Gustavo on 12/26/2017.
 */
interface UserEndpoint {

    @GET("user/add/{email}")
    fun addUser(@Path ("email")email: String): Observable<User>

}