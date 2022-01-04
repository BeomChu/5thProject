package com.super5th.ezen.config.auth;

import com.super5th.ezen.Repository.UserRepository;
import com.super5th.ezen.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PrincipalDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // 이부분에서 username파라미터에서 로그인을 이메일로 할것이기 때문에 findby만 email로 커스텀함
        // 맘에 안들면 나중에 findbyname 혹은 findbyUsername으로 변경하면 됨
        User userEntity = userRepository.findByEmail(username);

        if (userEntity == null) {
            return null;
        } else {
            return new PrincipalDetails(userEntity); // 이메일로 검색해서 있으면 세션에 저장해줌줌
        }
    }

}
