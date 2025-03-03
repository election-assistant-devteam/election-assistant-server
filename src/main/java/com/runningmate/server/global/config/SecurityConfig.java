package com.runningmate.server.global.config;

import com.runningmate.server.global.jwt.JwtAuthenticationFilter;
import com.runningmate.server.global.jwt.JwtUtil;
import com.runningmate.server.global.security.CustomAuthenticationEntryPoint;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
    private final JwtUtil jwtUtil;
    private final UserDetailsService userDetailsService;
    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final CustomAuthenticationEntryPoint customAuthenticationEntryPoint;
    private final String[] SWAGGER_ENDPOINTS = {
            "/swagger-ui/**", "/api-docs", "/swagger-ui-custom.html",
            "/v3/api-docs/**", "/api-docs/**", "/swagger-ui.html", "/swagger-ui/index.html"
    };

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
                        .requestMatchers(SWAGGER_ENDPOINTS).permitAll()
                        .anyRequest().authenticated()
                );

        // 토큰 검증 필터 추가
        http
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        http
                .exceptionHandling(configurer->configurer.authenticationEntryPoint(customAuthenticationEntryPoint));

        return http.build();
    }
}
