package com.gfb.watchlist.util.converters;

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

    public ResponseConverter(Converter<ResponseBody,
            Response<T>> converter) {
        this.converter = converter;
    }

    @Override
    public T convert(ResponseBody value) throws IOException {
        try {
            Response<T> response = converter.convert(value);
            if (response.getStatus()) {
                return response.getData();
            }
            throw new ServerException(response.getMessage());
        } catch (Exception e) {
            throw e;
        }
    }
}
