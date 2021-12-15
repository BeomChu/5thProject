package com.super5th.ezen.dto;


import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserUpdateDto {

    @NotNull
    private String password;
    @NotNull
    private String name;


}
