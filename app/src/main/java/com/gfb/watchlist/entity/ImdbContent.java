package com.gfb.watchlist.entity;

/**
 * Created by Gustavo on 12/26/2017.
 */

public class ImdbContent {

    private String title;

    private String year;

    private String genre;

    private Type type;

    private String poster;


    public enum Type{
        movie,
        series
    }
}
