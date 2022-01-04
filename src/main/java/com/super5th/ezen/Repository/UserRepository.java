package com.super5th.ezen.Repository;

import com.super5th.ezen.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User,Integer> {

    // 세션에 등록하기 위해 메서드 커스텀
    User findByEmail(String email);


}
