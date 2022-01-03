package com.super5th.ezen.Repository;

import com.super5th.ezen.entity.Genres;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface GenresRepository extends JpaRepository<Genres,Integer> {


    //장르명만 받아서 만듬
    @Modifying
    @Query(value ="INSERT INTO genres(genre,createDate) VALUES(:genre,now())",nativeQuery = true)
    void createGenre(String genre);


    //혹시 삭제할때 아이디 넘겨받아서 삭제
    @Modifying
    @Query(value ="DELETE FROM genres WHERE id = :id",nativeQuery = true)
    void deleteGenre(int id);
}
