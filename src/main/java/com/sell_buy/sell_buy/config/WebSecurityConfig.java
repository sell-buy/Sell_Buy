package com.sell_buy.sell_buy.config;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import java.util.HashMap;
import java.util.Map;

@EnableWebSecurity
@Configuration
@RequiredArgsConstructor
public class WebSecurityConfig {


    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .sessionManagement(sessionManagement
                        -> sessionManagement.sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED))
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(authorize -> authorize
                        .anyRequest().permitAll())
                .formLogin((formLogin) -> formLogin
                        .loginPage("/member/login")
                        .loginProcessingUrl("/member/login")
                        .usernameParameter("loginId")
                        .passwordParameter("password")
                        .successHandler(jsonAuthenticationSuccessHandler())
                        .failureHandler(jsonAuthenticationFailureHandler())
                        .defaultSuccessUrl("/")
                )
                .sessionManagement((sessionManagement) ->
                        sessionManagement.maximumSessions(1).expiredUrl("/session-expired"))
                .logout(logout -> logout
                        .logoutUrl("/member/logout")
                        .addLogoutHandler((request, response, authentication) -> {
                            HttpSession session = request.getSession(false);
                            if (session != null) {
                                session.invalidate();
                            }
                        })
                        .logoutSuccessHandler((request, response, authentication) -> {
                            response.setStatus(HttpServletResponse.SC_OK);
                            response.sendRedirect("/");
                        })
                        .logoutSuccessUrl("/")
                        .deleteCookies("JSESSIONID")
                );
        return http.build();
    }

    private AuthenticationSuccessHandler jsonAuthenticationSuccessHandler() {
        return (request, response, authentication) -> {
            HttpSession session = request.getSession();
            session.setAttribute("memId", authentication.getName());
            session.setMaxInactiveInterval(3600);

            response.setStatus(HttpServletResponse.SC_OK);

            Map<String, Object> responseData = new HashMap<>();
            responseData.put("status", "success");
            responseData.put("message", "Login successful");

        };
    }

    private AuthenticationFailureHandler jsonAuthenticationFailureHandler() {
        return (request, response, exception) -> {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);

            Map<String, Object> responseData = new HashMap<>();
            responseData.put("status", "error");
            responseData.put("message", "Login failed: " + exception.getMessage());

        };
    }
}
