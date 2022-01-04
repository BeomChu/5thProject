package com.super5th.ezen.config;

import com.super5th.ezen.config.oauth2.PrincipalOauth2Service;
import com.super5th.ezen.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final PrincipalOauth2Service principalOauth2Service;


//
    @Bean //IOC컨테이너에 등록
    public BCryptPasswordEncoder encodePwd(){          //비밀번호 해석할때 필요해서 걸어둠?
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.csrf().disable();
        http.authorizeRequests()
                .antMatchers("/test").authenticated()
                .anyRequest().permitAll()
                .and()
                .oauth2Login()
                .loginPage("/index")
                .userInfoEndpoint()
                .userService(principalOauth2Service);

        ////        super.configure(http);
//        http.authorizeRequests()
//                .antMatchers("/auth/**", "/", "static/**").permitAll() //아무나 들어감
////                .antMatchers("/v/**").access("hasRole('ROLE_VIEW')") //ROLE_VIEW있어야 들어감 //User클래스에 Rol없어서 패스
//                .anyRequest().authenticated()
//                .and()
//                .formLogin().loginPage("/").defaultSuccessUrl("/main").permitAll()
//                .usernameParameter("username").passwordParameter("password")
//                .and()
//                .logout().permitAll() //로그아웃은 아무나
//                .and()
//                .csrf().disable();              // 테스트 단계에선 csrf 꺼놓겠음
        http.csrf().disable();
//                csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse());


    }
//
//    @Override
//    public void configure(WebSecurity web) throws Exception{
//        web.ignoring().antMatchers("/static/**"); //이미지파일 같은거(정적파일)은 보안통과
//    }
//
//}
}