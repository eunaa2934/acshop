package com.naeun2934.acshop.config;

import com.naeun2934.acshop.user.UserOAuth2UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(securedEnabled = true, prePostEnabled = true)
public class SecurityConfig {

    private final UserOAuth2UserService userOAuth2UserService;

    @Autowired
    public SecurityConfig(UserOAuth2UserService userOAuth2UserService) {
        this.userOAuth2UserService = userOAuth2UserService;
    }

    @Bean
    public BCryptPasswordEncoder encoderPwd() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf().disable();

        http
                //- .authorizeHttpRequests() : 요청에 대해 권한을 체크하겠다.
                .authorizeHttpRequests()
                .antMatchers("/", "/users/**", "/emails/**", "/products/**", "/changeLanguage", "/css/**", "/images/**", "/js/**", "/pdf/**", "/favicon.ico").permitAll()
                //.anyRequest() : 그 외의 요청들은 | .authenticated() : 인증이 필요하다.
                .anyRequest().authenticated()
                // 직접 만든 폼 로그인
                .and().formLogin()
                .loginPage("/users/loginForm") //로그인 페이지 url
                .loginProcessingUrl("/users/login") //이 url을 로그인 기능을 담당하게 함
                .successForwardUrl("/users/loginSuccess") // 로그인이 성공하 경우 url
                .failureUrl("/users/loginForm?error=true") // 로그인이 실패할 경우 url
                .usernameParameter("userEmail")
                .passwordParameter("userPwd")
                .and()
                .logout()
                .logoutUrl("/users/logout") // 로그아웃시 url
                .logoutSuccessUrl("/")
                .invalidateHttpSession(true)
                .deleteCookies("JSESSIONID")
                // OAuth 로그인
                .and().oauth2Login().loginPage("/users/loginForm") //로그인 페이지 url
                .userInfoEndpoint().userService(userOAuth2UserService)
        ;//OAuth 가 들어오면 이 서비스로 매핑됨
        //  status code 핸들링
        http
                .exceptionHandling().authenticationEntryPoint((request, response, authException) -> {
                    response.sendRedirect("/users/loginForm");
                }).accessDeniedHandler((request, response, accessDeniedException) -> {
                    response.sendRedirect("/denied");
                });
        return http.build();
    }

}