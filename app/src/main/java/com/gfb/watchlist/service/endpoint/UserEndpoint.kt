package com.gfb.watchlist.service.endpoint

import com.gfb.watchlist.entity.Result
import com.gfb.watchlist.entity.User
import com.gfb.watchlist.entity.dto.UserDTO
import io.reactivex.Observable
import retrofit2.http.Body
import retrofit2.http.POST


/**
 * Created by Gustavo on 12/26/2017.
 */
interface UserEndpoint {

    @POST("user/validate")
    fun validateUser(@Body user: UserDTO): Observable<User>

    @POST("user/add")
    fun addUser(@Body user: UserDTO): Observable<User>

    @POST("user/changePassword")
    fun changePassword(@Body user: UserDTO): Observable<User>

    @POST("user/googleSignIn")
    fun googleSignIn(@Body user: UserDTO): Observable<User>
}