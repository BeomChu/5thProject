package com.super5th.ezen.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.lang.reflect.Array;
import java.util.Date;


@Entity
@Data
public class User {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false, unique = true) //중복안되게 포함
    private String email;
    @Column(nullable = false)
    private String password;
    @Column(nullable = false)
    private String name;

//    private Role role;              //enum이나 String으로 role만들어줘야함. 시큐리티도 수정예쩡
//    private Array category;

    public static User addUser(String email,String password,String name){ //유저등록
        User newUser=new User();
        newUser.email=email;
        newUser.password=password;
        newUser.name=name;
        return newUser;
    }




}
