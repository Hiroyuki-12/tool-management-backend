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

/**
 * Spring Security の設定クラス。
 * - CSRFやCORSの設定
 * - 認証が必要なパスと不要なパスの指定
 * - フォームログインやログアウトの挙動
 * - 認証失敗・未認証時のレスポンス制御
 * - Basic認証の有効化（簡易的なAPI認証用）
 */

@Configuration
@EnableWebSecurity

/**
 * セキュリティフィルタチェーンを定義する。
 * - /login, /logout は誰でもアクセス可能
 * - その他のURLはすべて認証が必要
 * - フォームログインの成功時・失敗時のレスポンスをカスタマイズ
 * - 認証エラー時は401 Unauthorizedを返す
 * - CSRFは無効化（API用）
 * - CORSは有効（フロントエンドと連携するため）
 */

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

  /**
   * CORS（クロスオリジンリソース共有）を細かく設定するためのBean定義。
   * - 指定されたオリジン（例: フロントエンド）からのアクセスを許可
   * - 使用可能なHTTPメソッドやヘッダーの制限
   * - Cookieなど認証情報の共有を許可
   *
   * @return CORS設定を含むCorsConfigurationSourceオブジェクト
   */

  @Bean
  public CorsConfigurationSource corsConfigurationSource() {
    CorsConfiguration configuration = new CorsConfiguration();
    configuration.setAllowedOrigins(List.of("http://54.172.221.49"));  // ★ 修正：EC2のフロントURL
    configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "PATCH"));
    configuration.setAllowedHeaders(List.of("*"));
    configuration.setAllowCredentials(true);  // Cookieを許可

    UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
    source.registerCorsConfiguration("/**", configuration);
    return source;
  }

  /**
   * 開発用のインメモリユーザーを定義。
   * - 実際のユーザー情報をデータベースに保存せず、メモリ上に保持する
   * - `username: user`, `password: password` のログイン情報
   * - `{noop}` はパスワードの暗号化なし（平文）を指定（開発用）
   *
   * @return 開発用ユーザー情報を持つUserDetailsServiceの実装
   */

  @Bean
  public UserDetailsService userDetailsService() {
    UserDetails user = User.withUsername("user")
        .password("{noop}password")  // プレーンテキスト（開発用）
        .roles("USER")
        .build();
    return new InMemoryUserDetailsManager(user);
  }
}







