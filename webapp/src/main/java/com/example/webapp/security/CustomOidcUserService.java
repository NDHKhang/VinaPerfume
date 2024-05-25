package com.example.webapp.security;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserRequest;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserService;
import org.springframework.security.oauth2.core.oidc.OidcUserInfo;
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;

import java.util.*;
import java.util.stream.Collectors;

public class CustomOidcUserService extends OidcUserService {

    @Override
    public OidcUser loadUser(OidcUserRequest userRequest) {
        OidcUser oidcUser = super.loadUser(userRequest);

        // Extract username
        String username = "";
        OidcUserInfo oidcUserInfo =  oidcUser.getUserInfo();
        if (oidcUserInfo != null) {
            username = oidcUserInfo.getClaims().getOrDefault("nickname", "USER").toString();
        }

        Set<SimpleGrantedAuthority> mappedAuthorities = new HashSet<>();

        if (username.equalsIgnoreCase("admin")) {
            mappedAuthorities.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
        } else {
            mappedAuthorities.add(new SimpleGrantedAuthority("ROLE_USER"));
        }

        // Create a new oidcUser with the mapped authorities
        return new DefaultOidcUser(mappedAuthorities, oidcUser.getIdToken(), oidcUser.getUserInfo());
    }
}
