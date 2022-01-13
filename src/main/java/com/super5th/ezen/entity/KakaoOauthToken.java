package com.super5th.ezen.entity;

import lombok.Data;

@Data
public class KakaoOauthToken {

    private String access_token;
    private String token_type;
    private String refresh_token;
    private int expries_in;
    private String scope;
    private int refresh_token_expries_in;


}
