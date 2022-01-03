package com.super5th.ezen.service;

import com.google.gson.JsonObject;
import com.super5th.ezen.Repository.MovieRepository;
import com.super5th.ezen.dto.MovieListDto;
import com.super5th.ezen.dto.NaverMovieInfoDto;
import com.super5th.ezen.entity.Movie;
import lombok.RequiredArgsConstructor;
import org.hibernate.type.LocalDateType;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.data.jpa.convert.threeten.Jsr310JpaConverters;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.*;


@Service
@Transactional
@RequiredArgsConstructor
public class MovieService {

    private final MovieRepository movieRepository;

    public List<MovieListDto> getDailyMovieList(String targetDt){   //박스오피스 일간순위위

       String daliyUrl="http://www.kobis.or.kr/kobisopenapi/webservice/rest/boxoffice/searchDailyBoxOfficeList.json";
        daliyUrl += "?key=7729620603ee6765e8f4ef827c91ebdd";
        daliyUrl += "%TargetDt=" + targetDt;

        List<MovieListDto> movieListDtoList = new ArrayList<>();

        // 커넥션
        HttpURLConnection conn;

        String line="";
        String result="";
        try{
            //연결
            URL url= new URL(daliyUrl);
            conn = (HttpURLConnection) url.openConnection();

            //읽고
            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            while((line = br.readLine()) != null)
                result += line;

            //파싱
            JSONParser parser = new JSONParser();
            JSONObject jsonObject = (JSONObject) parser.parse((String) null);
            JSONObject json = (JSONObject) jsonObject.get("boxOfficeResult"); // 박스오피스에서 가져온 결과
            JSONArray jsonArray = (JSONArray) json.get("dailyBoxOfficeList"); // 그 안에 있는 당일 순위

            for (int i=0; i<jsonArray.size(); i++) {
                MovieListDto movieListDto = new MovieListDto();
                JSONObject obj= (JSONObject) jsonArray.get(i);
                movieListDto.setRank((String) obj.get("rank"));
                movieListDto.setMovieCd((String) obj.get("MovieCd"));
                movieListDto.setMovieNm((String) obj.get("MovieNm"));
                movieListDtoList.add(movieListDto);
            }

        }catch (Exception e){
            e.printStackTrace();
        }

        return movieListDtoList;

    }

    public List<MovieListDto> getWeeklyMovieList(String targetDt){        //박스오피스 주간순위


        String weeklyUrl="http://www.kobis.or.kr/kobisopenapi/webservice/rest/boxoffice/searchweeklyBoxOfficeList.json";
        weeklyUrl += "?key=7729620603ee6765e8f4ef827c91ebdd";
        weeklyUrl += "&TargetDt=" + targetDt;
        weeklyUrl += "&weekGb=0";

        List<MovieListDto> movieListDtoList = new ArrayList<>();

        // 커넥션
        HttpURLConnection conn;

        String line="";
        String result="";
        try{
            //연결
            URL url= new URL(weeklyUrl);
            conn = (HttpURLConnection) url.openConnection();

            //읽고
            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            while((line = br.readLine()) != null)
                result += line;

            //파싱
            JSONParser parser = new JSONParser();
            JSONObject jsonObject = (JSONObject) parser.parse((String) null);
            JSONObject json = (JSONObject) jsonObject.get("boxOfficeResult"); // 박스오피스에서 가져온 결과
            JSONArray jsonArray = (JSONArray) json.get("weeklyBoxOfficeList"); // 그 안에 있는 주간 순위

            for (int i=0; i<jsonArray.size(); i++) {
                MovieListDto movieListDto = new MovieListDto();
                JSONObject obj= (JSONObject) jsonArray.get(i);
                movieListDto.setRank((String) obj.get("rank"));
                movieListDto.setMovieCd((String) obj.get("MovieCd"));
                movieListDto.setMovieNm((String) obj.get("MovieNm"));
                movieListDtoList.add(movieListDto); //필드에 작성해둔 어레이 리스트
            }

        }catch (Exception e){
            e.printStackTrace();
        }

        return movieListDtoList;

    }

    public Movie getNaverMoiveInfo(String title){       //영화정보 가져오기
        String clientId = "CS1GwnfyXjGRxsUBuhSU";
        String clientSecret = "IXYzfrfboA";          //암호화 해야함 yml에 넣던지 해쉬화 하던지
        String reqUrl="https://openapi.naver.com/v1/search/movie.json?query=";

        Movie naverMovieInfoDto=new Movie();


        Map<String, String> requestHeader = new HashMap<>();
        requestHeader.put("X-naver-client-id", clientId);
        requestHeader.put("X-naver-client-secret",clientSecret);

        HttpURLConnection conn = null;
        String line="";
        String result="";

        try{
            //연결
            String keyowrd= URLEncoder.encode(title,"UTF-8");
            URL url= new URL(reqUrl+keyowrd);
            conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            for (Map.Entry<String, String> header : requestHeader.entrySet()) {
                conn.setRequestProperty(header.getKey(),header.getValue());
            }
            //읽고
            BufferedReader br=new BufferedReader(new InputStreamReader(conn.getInputStream()));
            while((line = br.readLine()) != null){
                result += line;
            }

            //파싱
            JSONParser parser=new JSONParser();
            JSONObject jsonObject= (JSONObject) parser.parse(result);        //gson 이랑 json simple이랑 햇갈림..ㅎ
            JSONArray jsonArray = (JSONArray) jsonObject.get("items");
            System.out.println(jsonArray);
            JSONObject temp = (JSONObject) jsonArray.get(0);
            naverMovieInfoDto.setTitle((String) temp.get("title"));
            naverMovieInfoDto.setLink((String) temp.get("link"));
            naverMovieInfoDto.setImage((String) temp.get("image"));
            naverMovieInfoDto.setSubtitle((String) temp.get("subtitle"));
            naverMovieInfoDto.setPubDate((String) temp.get("pubDate"));//
//            SimpleDateFormat dateFormat=new SimpleDateFormat("yyyy");        //제작년도 2020.1.1로나옴
//            Date date = dateFormat.parse((String) temp.get("pubDate"));
//            naverMovieInfoDto.setPubDate(date);
            naverMovieInfoDto.setDirector((String) temp.get("director"));
            naverMovieInfoDto.setActor((String) temp.get("actor"));
            Double userRating = Double.parseDouble((String) temp.get("userRating")); //혹시 별점순 랭킹할까봐 double로 받아둠
            naverMovieInfoDto.setUserRating(userRating); // 더 간단하게 하는 방법이 있을것.

            movieRepository.save(naverMovieInfoDto);     //장르를 제외한 모든 정보 저장



        }catch(Exception e){
            e.printStackTrace();
        }


        return naverMovieInfoDto;

    }
}
