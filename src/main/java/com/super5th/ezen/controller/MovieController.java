package com.super5th.ezen.controller;

import com.super5th.ezen.dto.MovieListDto;
import com.super5th.ezen.service.MovieService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.SimpleDateFormat;
import java.util.*;

@Controller
@RequiredArgsConstructor
public class MovieController {

    private final MovieService movieService;

    @GetMapping("/movie/home")
    public String movieHome(){
        return "movie/home";
    }

    @PostMapping("/movie/add")
    public String saveMovie(String title){
        movieService.getNaverMoiveInfo(title);

        return "movie/home";
    }
    @GetMapping("movie/list")
    public String getMovieList(Model model){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        Calendar c1 = new GregorianCalendar();
        c1.add(Calendar.DATE, -1); // 오늘날짜로부터 -1
        String strDay = sdf.format(c1.getTime());
        System.out.println(strDay);

        Map<String,Object> listMap=new HashMap<>();

        List<MovieListDto> dailyMovieList = movieService.getDailyMovieList(strDay);
        List<MovieListDto> weeklyMovieList = movieService.getWeeklyMovieList(strDay);

        model.addAttribute("daily", dailyMovieList);
        model.addAttribute("weekly",weeklyMovieList);

        return "/movie/movielist";
    }
}
