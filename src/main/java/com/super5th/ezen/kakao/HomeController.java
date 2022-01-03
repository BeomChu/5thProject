package com.super5th.ezen.kakao;

import java.util.HashMap;

import javax.servlet.http.HttpSession;

import com.super5th.ezen.api.KakaoAPI;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
public class HomeController {

    KakaoAPI kakaoApi = new KakaoAPI();

    @RequestMapping(value="/login")
    public ModelAndView login(@RequestParam("code") String code, HttpSession session) {
        // redirect된 코드 넘겨받아서
        ModelAndView mav = new ModelAndView();
        // 1번 인증코드 요청 전달
        String accessToken = kakaoApi.getAccessToken(code);
        // 2번 인증코드로 토큰 전달
        HashMap<String, Object> userInfo = kakaoApi.getUserInfo(accessToken);

        System.out.println("login info : " + userInfo.toString());
        System.out.println("httpsession : " + session.toString());

        if(userInfo.get("email") != null) {
            session.setAttribute("userId", userInfo.get("email"));
            session.setAttribute("accessToken", accessToken);
            System.out.println("session: "+session.toString());
            System.out.println("session id: "+session.getId());
            System.out.println("session all: " + session.getAttributeNames().toString());
        }
        mav.addObject("userId", userInfo.get("email"));
        mav.setViewName("index"); //if문 활용해서 회원가입으로 넘어가거나 홈페이지로 넘어가게 함
        return mav;
    }

    @RequestMapping(value="/logout")
    public ModelAndView logout(HttpSession session) {
        ModelAndView mav = new ModelAndView();

        kakaoApi.kakaoLogout((String)session.getAttribute("accessToken"));
        session.removeAttribute("accessToken");
        session.removeAttribute("userId");
        mav.setViewName("index");
        return mav;
    }


}