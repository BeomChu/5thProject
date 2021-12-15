package com.super5th.ezen.entity;

import com.sun.istack.NotNull;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

public class Movie {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotNull
    private String movie_name;

    @NotNull
    private String synopsis;     //개요

    private double score;
//    private List<?> category;


    public static Movie addMoive(String movie_name,String synopsis){ //영화등록
        Movie newMovie=new Movie();
        newMovie.movie_name =movie_name;
        newMovie.synopsis =synopsis;

        return newMovie;



    }
}
