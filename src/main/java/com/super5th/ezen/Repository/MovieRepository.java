package com.super5th.ezen.Repository;

import com.super5th.ezen.entity.Movie;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MovieRepository extends JpaRepository<Movie,Integer> {
}
