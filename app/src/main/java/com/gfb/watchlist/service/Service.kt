package com.gfb.watchlist.service

import com.gfb.watchlist.BuildConfig
import com.gfb.watchlist.util.converters.ResponseConverterFactory
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory


/**
 * Created by Gustavo on 10/16/2017.
 */

object Service {

    private val builder = Retrofit.Builder()
            .baseUrl(BuildConfig.URL_BASE)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(ResponseConverterFactory(GsonConverterFactory.create()))
            .addConverterFactory(GsonConverterFactory.create())


    fun <S> createService(serviceClass: Class<S>): S {
        val retrofit = builder
                .build()
        return retrofit.create(serviceClass)
    }

}
