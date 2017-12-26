package com.gfb.watchlist.entity;

public class Response {

    private boolean status;

    private String message;

    public Response(boolean status, String message) {
        this.status = status;
        this.message = message;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public static Response ok(String s) {
        return new Response(true, s);
    }

    public static Response error(String s) {
        return new Response(false, s);
    }
}
