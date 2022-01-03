package com.super5th.ezen.dto;

import lombok.Data;

import java.util.Date;

@Data
public class NaverMovieInfoDto {
    private String title;
    private String link;
    private String image;
    private String subtitle;
    private String pubDate;
    private String director;
    private String actor;
    private Double userRating;
}
