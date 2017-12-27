package com.gfb.watchlist.util;

/**
 * Created by Gustavo on 12/27/2017.
 */

public class Error {

    private String message;

    public Error(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
