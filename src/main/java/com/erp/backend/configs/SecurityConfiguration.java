package com.erp.backend.configs;

import com.erp.backend.configs.filters.ChainExceptionHandlerFilter;
import com.erp.backend.configs.filters.JwtAuthenticationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;

import java.util.Arrays;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor

public class SecurityConfiguration   {
    private final JwtAuthenticationFilter jwtAuthFilter;
    private final AuthenticationProvider authenticationProvider;
    private final LogoutHandler logoutHandler;

    private final ChainExceptionHandlerFilter chainExceptionHandlerFilter;
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .cors(cors -> {
                    CorsConfigurationSource source = request -> {
                        CorsConfiguration config = new CorsConfiguration();
                        config.setAllowedOrigins(Arrays.asList("http://localhost:3000"));
                        config.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
                        config.setAllowedHeaders(Arrays.asList("Authorization", "Content-Type"));
                        return config;
                    };
                    cors.configurationSource(source);
                })
                .csrf()
                .disable()
                .authorizeHttpRequests()
                .antMatchers("/api/signup/**","/api/login/**","/api/verification/**", "/swagger-ui/**","/swagger-resources/**",
                        "/swagger-ui.html",
                        "/api/article/getAll",
                        "/api/article/new",
                        "/api/article/getAllPage",

                        "/api/article/get/**",
                        "/api/article/category/**",
                        "/api/author/getAll",
                        "/api/author/get/**",
                        "/api/category/getAll",
                        "/api/category/get/**",
                        "/api/search/**",
                        "/api/search",
                        "/upload/**",
                        "/api/users/**",
                        "/v2/api-docs",
                        "/webjars/**")
                .permitAll()
                .antMatchers("/api/article/favorite/**",
                        "/api/article/unfavorite/**",
                        "/api/comment/create",
                        "/api/users/**")
               // .hasAuthority("USER")
                .hasAnyAuthority("USER", "ADMIN")
                .antMatchers( "/api/article/create",
                        "/api/author/create",
                        "/api/category/create",
                        "/api/category/update",
                        "/api/category/delete/**",
                        "/api/article/update",

                        "/api/author/update",
                        "/api/author/delete/**"

                        )
                .hasAuthority("ADMIN")

                .anyRequest()
                .authenticated()
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(chainExceptionHandlerFilter, JwtAuthenticationFilter.class)
                .logout()
                .logoutUrl("/api/v1/auth/logout")
                .addLogoutHandler(logoutHandler)
                .logoutSuccessHandler((request, response, authentication) -> SecurityContextHolder.clearContext())

        ;

        return http.build();
    }

}
