package com.super5th.ezen.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.super5th.ezen.config.auth.PrincipalDetails;
import com.super5th.ezen.entity.KakaoOauthToken;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class UserController {


    @GetMapping("/test/help")
    public String principalTest(@AuthenticationPrincipal PrincipalDetails principalDetails){
        return principalDetails.toString();
    }


    @GetMapping("/auth/kakao/callback")
    public @ResponseBody String kakaoCallBack(String code){

        RestTemplate rt= new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-type","application/x-www-form-urlencoded;charset=utf-8");

        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("grant_type","authorization_code");
        params.add("client_id","264edb35a42e39dc36007baf5a6c82a7");
        params.add("redirect_url","http://localhost:8080/auth/kakao/callback");
        params.add("code",code);

        HttpEntity<MultiValueMap<String,String>> kakaoTokenRequest =
                new HttpEntity<>(params,headers);

        ResponseEntity<String> response=rt.exchange(
          "https://kauth.kakao.com/oauth/token",
                HttpMethod.POST,
                kakaoTokenRequest,
                String.class
        );
        ObjectMapper obMapper = new ObjectMapper();
        KakaoOauthToken oauthToken = null;
        try {
           oauthToken = obMapper.readValue(response.getBody(), KakaoOauthToken.class);
        } catch (JsonMappingException e) {
            e.printStackTrace();
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        System.out.println("토큰: "+oauthToken);

//        RestTemplate rt2= new RestTemplate();
//        HttpHeaders headers2 = new HttpHeaders();
//        headers2.add("Authorization","Bearer "+oauthToken.getAccess_token());
//        headers2.add("Content-type","application/x-www-form-urlencoded;charset=utf-8");
//
//        HttpEntity<MultiValueMap<String,String>> kakaoProfileRequest2 =
//                new HttpEntity<>(headers2);
//
//        ResponseEntity<String> response2=rt2.exchange(
//                "https://kapi.kakao.com/v2/user/me",
//                HttpMethod.POST,
//                kakaoProfileRequest2,
//                String.class
//        );

        return response.getBody();
    }
}
