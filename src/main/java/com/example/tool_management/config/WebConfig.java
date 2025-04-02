package com.example.tool_management.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * WebアプリケーションのCORS設定を行う設定クラス。
 * フロントエンド（Reactなど）からのクロスオリジンアクセスを許可する。
 */

@Configuration
public class WebConfig {

  /**
   * WebMvcConfigurerのBeanを定義し、CORS設定を追加する。
   * - すべてのパス（/**）に対して設定を適用
   * - 特定のオリジン（http://54.172.221.49:3000）を許可
   * - 許可するHTTPメソッドを指定
   * - Cookieなどの認証情報を含めることを許可
   */

  @Bean
  public WebMvcConfigurer corsConfigurer() {
    return new WebMvcConfigurer() {
      @Override
      public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
            .allowedOrigins("http://54.172.221.49:3000")  // フロントエンドのURL
            .allowedMethods("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS")
            .allowCredentials(true);
      }
    };
  }
}
