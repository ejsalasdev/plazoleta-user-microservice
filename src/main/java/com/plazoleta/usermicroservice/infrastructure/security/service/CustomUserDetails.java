package com.plazoleta.usermicroservice.infrastructure.security.service;

import java.util.Collection;
import java.util.Collections;
import java.util.Objects;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;

import com.plazoleta.usermicroservice.domain.model.UserModel;

import lombok.Getter;

@Getter
public class CustomUserDetails extends User {
    
    private final Long userId;

    public CustomUserDetails(String username, String password, Collection<? extends GrantedAuthority> authorities, Long userId) {
        super(username, password, authorities);
        this.userId = userId;
    }
    
    public static CustomUserDetails fromUserModel(UserModel userModel){
        SimpleGrantedAuthority authorities = new SimpleGrantedAuthority(userModel.getRole().getRoleEnum().name());
        
        return new CustomUserDetails(
                userModel.getEmail(),
                userModel.getPassword(),
                Collections.singletonList(authorities),
                userModel.getId()
        );
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        CustomUserDetails that = (CustomUserDetails) o;
        return Objects.equals(userId, that.userId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), userId);
    }
}
