package com.super5th.ezen.service;

import com.super5th.ezen.Repository.UserRepository;
import com.super5th.ezen.dto.UserUpdateDto;
import com.super5th.ezen.entity.User;
import lombok.RequiredArgsConstructor;

import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;

@Service
@Transactional
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder encoder;        //비밀번호 해쉬처리해주는거

    public int 회원가입(User user){
        String rawPassword = user.getPassword();          //기존 비밀번호 변수에 넣어주고
        String encPassword = encoder.encode(rawPassword);// 해쉬화 해서 레파지토리에 넣어줄거임
        user.setPassword(encPassword);                   // user 객체에 비밀번호 변경해서 넣어줌
        userRepository.save(user);

        return user.getId();         //테스트 코드 작성할때 리턴값 int로 받으면 편해서 일단

    }

    public int 회원수정(int userId,UserUpdateDto userUpdateDto){
        User updateUser = userRepository.findById(userId).orElseThrow(()->new EntityNotFoundException("아이디를 찾을 수 없습니다."));// 핸들러 만들면 수정 예정

        String rawPassword = userUpdateDto.getPassword();
        String encPassword = encoder.encode(rawPassword);  //위와 동일하게 비밀번호 해쉬화

        updateUser.setPassword(encPassword);               //Dto에 비밀번호랑 이름만 수정할 수 있게 했음
        updateUser.setName(userUpdateDto.getName());

        userRepository.save(updateUser);                   //더티체킹해서 알아서 레파지토리에 저장해줌(세션 값이랑 비교함)

        return updateUser.getId();

    }

    //조회나 삭제는 필요하면 만들겠음.





}
