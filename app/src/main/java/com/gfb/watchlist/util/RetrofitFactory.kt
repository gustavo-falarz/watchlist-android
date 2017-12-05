package com.gfb.watchlist.util

import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Created by Gustavo on 10/27/2017.
 */
object RetrofitFactory {

    fun getRetrofit(): Retrofit = Retrofit.Builder()
            .baseUrl(Constants.WS_URL_BASE)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()!!

}