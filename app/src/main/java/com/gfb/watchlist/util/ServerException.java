package com.gfb.watchlist.util;

import java.io.IOException;

/**
 * Created by Gustavo on 10/19/2017.
 */

public class ServerException extends IOException {

    private String message;

    public ServerException(String message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
