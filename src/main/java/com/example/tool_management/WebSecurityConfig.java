package com.example.tool_management;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    http
        .csrf(csrf -> csrf.disable())  // CSRF無効（API用）
        .cors(cors -> {})              // CORS有効化
        .authorizeHttpRequests(auth -> auth
            .requestMatchers("/login", "/logout").permitAll()  // loginとlogoutは常に許可
            .anyRequest().authenticated()                      // それ以外は認証必須
        )
        .formLogin(form -> form
            .loginProcessingUrl("/login")  // フロントエンドがPOSTするURL
            .successHandler((request, response, authentication) -> {
              response.setStatus(HttpServletResponse.SC_OK);  // 成功時は200 OK
            })
            .failureHandler((request, response, exception) -> {
              response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);  // 失敗時は401
            })
            .permitAll()
        )
        .logout(logout -> logout.permitAll())
        .exceptionHandling(exception ->
            exception.authenticationEntryPoint((request, response, authException) -> {
              response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);  // 未認証時は401
            })
        )
        .httpBasic();  // ← Basic認証有効化

    return http.build();
  }

  @Bean
  public CorsConfigurationSource corsConfigurationSource() {
    CorsConfiguration configuration = new CorsConfiguration();
    configuration.setAllowedOrigins(List.of("http://54.81.159.176:3000"));  // ★ 修正：EC2のフロントURL
    configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "PATCH"));
    configuration.setAllowedHeaders(List.of("*"));
    configuration.setAllowCredentials(true);  // Cookieを許可

    UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
    source.registerCorsConfiguration("/**", configuration);
    return source;
  }

  @Bean
  public UserDetailsService userDetailsService() {
    UserDetails user = User.withUsername("user")
        .password("{noop}password")  // プレーンテキスト（開発用）
        .roles("USER")
        .build();
    return new InMemoryUserDetailsManager(user);
  }
}







