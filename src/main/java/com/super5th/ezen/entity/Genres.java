package com.super5th.ezen.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
public class Genres {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    public int id;

    private String genre;

    private LocalDate createDate;

    @PrePersist
    public void createDate(){
        this.createDate=LocalDate.now();
    }

}
