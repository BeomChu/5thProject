package com.super5th.ezen.controller;

import com.super5th.ezen.config.auth.PrincipalDetails;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {


    @GetMapping("/test/help")
    public String principalTest(@AuthenticationPrincipal PrincipalDetails principalDetails){
        return principalDetails.toString();
    }
}
