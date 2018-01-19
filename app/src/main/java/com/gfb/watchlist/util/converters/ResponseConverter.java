package com.gfb.watchlist.util.converters;

import android.support.annotation.NonNull;

import com.gfb.watchlist.entity.Response;
import com.gfb.watchlist.util.ServerException;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Converter;

/**
 * Created by Gustavo on 10/19/2017.
 */

class ResponseConverter<T> implements Converter<ResponseBody, T> {
    private Converter<ResponseBody, Response<T>> converter;

    ResponseConverter(Converter<ResponseBody,
            Response<T>> converter) {
        this.converter = converter;
    }

    @Override
    public T convert(@NonNull ResponseBody value) throws IOException {
        Response<T> response = converter.convert(value);
        if (response.getStatus()) {
            return response.getData();
        }
        throw new ServerException(response.getMessage());
    }
}
