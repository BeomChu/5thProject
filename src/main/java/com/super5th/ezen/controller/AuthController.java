package com.super5th.ezen.controller;

import com.super5th.ezen.dto.NaverMovieInfoDto;
import com.super5th.ezen.entity.Movie;
import com.super5th.ezen.service.MovieService;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import net.bytebuddy.implementation.bytecode.assign.TypeCasting;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;


@Controller
@RequiredArgsConstructor
public class AuthController {

    private final MovieService movieService;


    @GetMapping("test/naver")
    public String testcase() {
        Movie moiveInfo = movieService.getNaverMoiveInfo("킹스맨: 퍼스트 에이전트");

        return moiveInfo.toString();
    }
    @GetMapping("/test")
    public String dd(){
        return "test";
    }

    public static void main(String[] args) {
        Set<String> testset = new HashSet<>();
        testset.add("일");
        testset.add("이");
        testset.add("삼");

        Set<String> testcase = new HashSet<>();
        testcase.add("일");
        testcase.add("사");
        testcase.add("오");

        for (String s : testcase) {
            if(testset.contains(s)){
                System.out.println(s);
            }
        }




    }

}


