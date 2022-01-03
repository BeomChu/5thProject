package com.super5th.ezen.entity;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
public class MovieGenres {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn (name = "movie_id")
    private Movie movie;

    @ManyToOne
    @JoinColumn (name = "genres_id")
    private Genres genres;

    private LocalDate createDate;

    @PrePersist
    public void createDate(){
        this.createDate=LocalDate.now();
    }

}
