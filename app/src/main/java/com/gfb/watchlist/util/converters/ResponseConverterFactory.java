package com.gfb.watchlist.util.converters;

import com.gfb.watchlist.entity.Response;

import java.lang.annotation.Annotation;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import okhttp3.ResponseBody;
import retrofit2.Converter;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Gustavo on 10/19/2017.
 */

public class ResponseConverterFactory extends Converter.Factory {

    private GsonConverterFactory factory;

    public ResponseConverterFactory(GsonConverterFactory factory) {
        this.factory = factory;
    }

    @Override
    public Converter<ResponseBody, ?> responseBodyConverter(final Type type,
                                                            Annotation[] annotations, Retrofit retrofit) {
        Type wrappedType = new ParameterizedType() {
            @Override
            public Type[] getActualTypeArguments() {
                return new Type[]{type};
            }

            @Override
            public Type getOwnerType() {
                return null;
            }

            @Override
            public Type getRawType() {
                return Response.class;
            }
        };
        Converter<ResponseBody, ?> gsonConverter = factory
                .responseBodyConverter(wrappedType, annotations, retrofit);
        return new ResponseConverter(gsonConverter);
    }
}
