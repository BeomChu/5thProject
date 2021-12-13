package com.super5th.ezen.controller;

import net.bytebuddy.build.Plugin;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class AuthController {

//    @GetMapping("/login")
//    public String loginform(){
//        return "/";
//    }

    @GetMapping("/singnin")
    public String signin(){
        return "/signin";
    }

    @GetMapping("/signup")
    public String signupform(){
        return "/signup";
    }

    @PostMapping
    public String signup(){
        return "/signin";
    }

    @GetMapping("/")
    public String login(@AuthenticationPrincipal User user){
        if(user!=null) {
            if (user.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_VIEW"))) {
                return "redirect:/v";
            }
        }
         return "redirect:/";
        }



}
