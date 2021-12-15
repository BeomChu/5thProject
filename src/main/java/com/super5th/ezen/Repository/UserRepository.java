package com.super5th.ezen.Repository;

import com.super5th.ezen.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User,Integer> {

}
