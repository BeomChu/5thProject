package com.super5th.ezen.config.auth;

import com.super5th.ezen.entity.User;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

@Data
public class PrincipalDetails implements UserDetails, OAuth2User {

    private static final Long serialVersionUID= 1L;

    private User user;
    private Map<String, Object> attributes;

    public PrincipalDetails(User user){
        this.user=user;
    }
    public PrincipalDetails(User user,Map<String, Object> attributes){
        this.user=user;
        this.attributes=attributes;
    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {

        Collection<GrantedAuthority> collector = new ArrayList<>();
        collector.add(()->{return user.getRole();});
        return collector;

    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getName();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public String getName() {
        return (String) attributes.get("name");
    }

    @Override //이부분에서 문제가 생길까 싶은데
    public <A> A getAttribute(String name) {
        return OAuth2User.super.getAttribute(name);
    }

}
