package com.example.webapp.security;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserService;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.IOException;

@Configuration
@EnableMethodSecurity(securedEnabled = true)
public class SecurityConfig {

    @Value("${okta.oauth2.issuer}")
    private String issuer;

    @Value("${okta.oauth2.client-id}")
    private String clientId;

    @Bean
    public SecurityFilterChain configure(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers("/", "/shop/**", "/images/**", "/login/**", "/logout/**", "/home/**",
                                "/resources/**", "/product/**", "/cart/**", "/checkout/**", "/api/**").permitAll()
                        .requestMatchers("/order/**").hasRole("USER")
                        .requestMatchers("/back/**").hasRole("ADMIN")
                        .anyRequest().authenticated()
                )
                .oauth2Login(oauth2 -> oauth2
                        .successHandler(customAuthenticationSuccessHandler())
                        .userInfoEndpoint(userInfoEndpoint ->
                                userInfoEndpoint.oidcUserService(oidcUserService()))
                )
//                .oauth2Login(withDefaults())
                // configure logout with Auth0
                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/home") // Đường dẫn chuyển hướng sau khi đăng xuất thành công
                        .invalidateHttpSession(true) // Hủy phiên làm việc (session) hiện tại
                        .deleteCookies("JSESSIONID") // Xóa cookie JSESSIONID (cookie lưu trữ thông tin phiên làm việc)
                        .clearAuthentication(true) // Xóa thông tin xác thực từ SecurityContext
                        .addLogoutHandler(logoutHandler()) // Thêm LogoutHandler tùy chỉnh (nếu có)
                        .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                )
                .exceptionHandling(exceptionHandling ->
                        exceptionHandling.accessDeniedPage("/error/403")
                );
        return http.build();
    }

    private LogoutHandler logoutHandler() {
        return (request, response, authentication) -> {
            try {
                String baseUrl = ServletUriComponentsBuilder.fromCurrentContextPath().build().toUriString();
                response.sendRedirect(issuer + "v2/logout?client_id=" + clientId + "&returnTo=" + baseUrl);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        };
    }

    @Bean
    public AuthenticationSuccessHandler customAuthenticationSuccessHandler() {
        return new CustomAuthenticationSuccessHandler();
    }

    @Bean
    public OidcUserService oidcUserService() {
        return new CustomOidcUserService();
    }

}
