package com.super5th.ezen.service;


import com.super5th.ezen.Repository.GenresRepository;
import com.super5th.ezen.entity.Genres;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class GenresService {

    private final GenresRepository genresRepository;

    public void 장르등록(String genre){
        genresRepository.createGenre(genre);
    }

    public void 장르삭제(int genreId){
        genresRepository.deleteGenre(genreId);
    }

}
