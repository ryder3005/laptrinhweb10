package com.baitap10.conf;

import com.baitap10.filter.JwtAuthenticationFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {

    private final AuthenticationProvider authenticationProvider;
    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    public SecurityConfiguration(
            JwtAuthenticationFilter jwtAuthenticationFilter,
            AuthenticationProvider authenticationProvider
    ) {
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
        this.authenticationProvider = authenticationProvider;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        // Sử dụng String trực tiếp thay vì khởi tạo AntPathRequestMatcher thủ công
                        .requestMatchers("/auth/**", "/login**", "/user/**").permitAll()
                        .requestMatchers("/images/**", "/js/**").permitAll()
                        .anyRequest().authenticated()
                )
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();

        // 1. Cho phép các nguồn cụ thể (Origin)
        configuration.setAllowedOrigins(List.of("http://localhost:8005"));

        // 2. Cho phép các phương thức HTTP
        configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "PATCH", "HEAD", "OPTIONS"));

        // 3. Cho phép gửi kèm Credentials (như Cookies hoặc Header xác thực)
        configuration.setAllowCredentials(true);

        // 4. Cho phép các Headers cụ thể từ phía Client
        configuration.setAllowedHeaders(List.of("Authorization", "Content-Type", "Cache-Control"));

        // 5. Cấu hình đường dẫn áp dụng
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);

        return source;
    }
}