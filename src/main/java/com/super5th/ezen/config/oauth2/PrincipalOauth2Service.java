package com.super5th.ezen.config.oauth2;

import com.super5th.ezen.Repository.UserRepository;
import com.super5th.ezen.config.auth.PrincipalDetails;
import com.super5th.ezen.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PrincipalOauth2Service extends DefaultOAuth2UserService {

    private final UserRepository userRepository; // 아이디비번 저기다 박을꺼얌


    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        System.out.println("userRequest:"+userRequest+"----");
        System.out.println("accesstoken:"+userRequest.getAccessToken().getTokenValue());
        System.out.println(userRequest.getAccessToken().getScopes());
        System.out.println("attributes:"+super.loadUser(userRequest).getAttributes());
        OAuth2User oAuth2User=super.loadUser(userRequest);
        Map<String,Object> userinfo = oAuth2User.getAttributes();

        User findByEmail = userRepository.findByEmail((String)userinfo.get("email"));

        if(findByEmail==null){

            User userEntity = new User();
            userEntity.setName((String)userinfo.get("name"));
            userEntity.setEmail((String)userinfo.get("email"));
            userEntity.setRole("ROLE_USER");
            userEntity.setPassword(new BCryptPasswordEncoder().encode(UUID.randomUUID().toString()));

            return new PrincipalDetails(userRepository.save(userEntity),oAuth2User.getAttributes());
        }else{
            return new PrincipalDetails(findByEmail,oAuth2User.getAttributes());
        }

    }
}
