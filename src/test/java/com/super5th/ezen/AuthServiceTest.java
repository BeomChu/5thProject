package com.super5th.ezen;


import com.super5th.ezen.Repository.UserRepository;
import com.super5th.ezen.dto.UserUpdateDto;
import com.super5th.ezen.entity.User;
import com.super5th.ezen.service.AuthService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import static org.junit.jupiter.api.Assertions.*;



@SpringBootTest
@Transactional
class AuthServiceTest {

    @Autowired
    private AuthService authService;
    @Autowired
    private UserRepository userRepository;


    //유저 객체 만들기
    public static User createUser(){
        User user=new User();
        user.setName("범츄");
        user.setPassword("1234");
        user.setEmail("이@메.일");

        return user;
    }
    public static UserUpdateDto createUserDto(){
        UserUpdateDto userUpdateDto=new UserUpdateDto("5678","범츄범츄");
        return userUpdateDto;
    }


    @Test
    @DisplayName("회원가입테스트")
    public void 회원가입테스트(){
        //given
        User user=createUser();
        //when
        int userid=authService.회원가입(user);
        //then
        userRepository.findById(userid).orElseThrow(EntityNotFoundException::new);

    }
    @Test
    @DisplayName("회원수정테스트")
    public void 회원수정테스트(){
        //given
        User user=createUser();
        int userid=authService.회원가입(user);
        User updateUser=userRepository.findById(userid).orElseThrow(EntityNotFoundException::new);
        UserUpdateDto userUpdateDto=createUserDto();
        updateUser.setName(userUpdateDto.getName());
        updateUser.setPassword(userUpdateDto.getPassword());
        //when
        int 수정된거 = authService.회원수정(userid, userUpdateDto);
        System.out.println(수정된거);



        //then
        User user1 = userRepository.findById(수정된거).orElseThrow(EntityNotFoundException::new);
        String name=user1.getName();
        String pw=user1.getPassword();
        System.out.println(name);
        System.out.println(pw);
        assertEquals(user1.getName(),updateUser.getName());
    }


}
