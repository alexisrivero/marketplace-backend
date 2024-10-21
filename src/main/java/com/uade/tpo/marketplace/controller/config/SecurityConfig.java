package com.uade.tpo.marketplace.controller.config;

import com.uade.tpo.marketplace.entity.Role;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;



import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

        private final JwtAuthenticationFilter jwtAuthFilter;
        private final AuthenticationProvider authenticationProvider;

        @Bean
        public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
                http
                                .csrf(AbstractHttpConfigurer::disable)
                                .authorizeHttpRequests(req -> req
                                        .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()
                                        .requestMatchers("/api/v1/auth/**").permitAll()
                                        .requestMatchers("/error/**").permitAll()
                                        .requestMatchers(HttpMethod.GET, "/product/**").permitAll()
                                        .requestMatchers(HttpMethod.POST, "/product/**").hasAnyAuthority(Role.ADMIN.name())
                                        .requestMatchers(HttpMethod.DELETE, "/product/**").hasAnyAuthority(Role.ADMIN.name())
                                        .requestMatchers(HttpMethod.PUT, "/product/**").hasAnyAuthority(Role.ADMIN.name())
                                        .requestMatchers("/user/address").hasAnyAuthority(Role.USER.name(), Role.ADMIN.name())
                                        .requestMatchers("/user/payment-method").hasAnyAuthority(Role.USER.name(), Role.ADMIN.name())
                                        .requestMatchers("/checkout/**").hasAnyAuthority(Role.USER.name(), Role.ADMIN.name())
                                        .requestMatchers("/order/user/**").hasAnyAuthority(Role.USER.name(), Role.ADMIN.name())
                                        .requestMatchers("/user/**").hasAnyAuthority(Role.ADMIN.name())
                                        .requestMatchers("/order/elegible").hasAnyAuthority(Role.USER.name())
                                        .requestMatchers("/order/**").hasAnyAuthority(Role.ADMIN.name())
                                        .anyRequest().authenticated()
                        )
                                .sessionManagement(session -> session.sessionCreationPolicy(STATELESS))
                                .authenticationProvider(authenticationProvider)
                                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

                return http.build();
        }
}
