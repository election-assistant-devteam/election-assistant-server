package com.runningmate.server.global.config;

import com.runningmate.server.global.jwt.JwtUtil;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    private final JwtUtil jwtUtil;

    public SecurityConfig(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
        // csrf 비활성화
        http
                .csrf(csrfConfigurer->csrfConfigurer.disable());

        // 폼 로그인 비활성화
        http
                .formLogin(loginConfigurer->loginConfigurer.disable());

        //  기본 인증 로그인 사용 X
        http
                .httpBasic(httpBasicConfigurer -> httpBasicConfigurer.disable());

        // 인증정보를 서버에 담지 않음
        http
                .sessionManagement(sessionManagementConfigurer ->
                        sessionManagementConfigurer.sessionCreationPolicy(SessionCreationPolicy.STATELESS) );

        // 엔드포인트별 인증인가 정책 설정
        http
                .authorizeHttpRequests(authorizeHttpRequestCustomizer -> authorizeHttpRequestCustomizer
                        .requestMatchers("/auth/login", "/users").permitAll()
                        .anyRequest().authenticated()
                );

        return http.build();
    }
}
