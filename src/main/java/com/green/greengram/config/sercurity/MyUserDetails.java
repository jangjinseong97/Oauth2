package com.green.greengram.config.sercurity;

import com.green.greengram.config.jwt.JwtUser;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

@Getter
@Setter
public class MyUserDetails implements UserDetails , OAuth2User {

    private JwtUser jwtUser;

    @Override
    public Map<String, Object> getAttributes() {
        return Map.of();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // 권한 반환 이 url이 있는 사람에게 권한을 줌
        List<GrantedAuthority> authorities = new ArrayList<>(jwtUser.getRoles().size());
        for(String role : jwtUser.getRoles()) {
            authorities.add(new SimpleGrantedAuthority(role));
        }
        return List.of();
    }

    // 아래 두개는 따로 받아서 처리할거라 여기선 필요가 없다.

    @Override
    public String getPassword() {
        return null;
    }

    @Override
    public String getUsername() {
        return null;
    }

    @Override
    public String getName() {
        return "";
    }
}
