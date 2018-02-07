package com.gfb.watchlist.service.endpoint

import com.gfb.watchlist.entity.Result
import com.gfb.watchlist.entity.User
import com.gfb.watchlist.entity.dto.UserDTO
import io.reactivex.Observable
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path


/**
 * Created by Gustavo on 12/26/2017.
 */
interface UserEndpoint {

    @POST("user/add")
    fun addUser(@Body user: UserDTO): Observable<Result>

    @POST("user/validate")
    fun validateUser(@Body user: UserDTO): Observable<User>

    @POST("user/changePassword")
    fun changePassword(@Body user: UserDTO): Observable<User>

    @GET("user/forgotPassword/{email}")
    fun forgotPassword(@Path("email") email: String): Observable<Result>

    @POST("user/googleSignIn")
    fun googleSignIn(@Body user: UserDTO): Observable<User>
}