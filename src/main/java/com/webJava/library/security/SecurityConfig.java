package com.webJava.library.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

/**
 * Конфигурация безопасности приложения.
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig {
    private JwtAuthEntryPoint jwtAuthEntryPoint;

    @Autowired
    public SecurityConfig(JwtAuthEntryPoint jwtAuthEntryPoint) {
        this.jwtAuthEntryPoint = jwtAuthEntryPoint;
    }

    /**
     * Настраивает цепочку фильтров безопасности.
     *
     * @param httpSecurity объект HttpSecurity для настройки безопасности HTTP
     * @return объект SecurityFilterChain, представляющий цепочку фильтров безопасности
     * @throws Exception если возникает ошибка при настройке цепочки фильтров
     */
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
                .csrf(AbstractHttpConfigurer::disable)
                .cors(Customizer.withDefaults())
                .exceptionHandling(c -> c.authenticationEntryPoint(jwtAuthEntryPoint))
                .sessionManagement(c -> c.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(c -> c
                        .requestMatchers("/api/auth/**").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/role/*").permitAll()
                        .requestMatchers("/api/role/*").hasRole("ADMIN")
                        .requestMatchers("/api/user/current").authenticated()
                        .requestMatchers(HttpMethod.GET, "/api/**").permitAll()
                        .anyRequest().authenticated())
                .httpBasic(Customizer.withDefaults())
                .addFilterBefore(getJwtAuthFilter(), UsernamePasswordAuthenticationFilter.class)
                .build();
    }

    /**
     * Возвращает экземпляр AuthenticationManager.
     *
     * @param config объект AuthenticationConfiguration для настройки AuthenticationManager
     * @return объект AuthenticationManager для управления аутентификацией
     * @throws Exception если возникает ошибка при создании AuthenticationManager
     */
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    /**
     * Создает и возвращает экземпляр PasswordEncoder.
     *
     * @return объект PasswordEncoder для шифрования паролей
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * Создает и возвращает экземпляр JwtAuthFilter.
     *
     * @return объект JwtAuthFilter для обработки аутентификации с использованием JWT
     */
    @Bean
    public JwtAuthFilter getJwtAuthFilter() {
        return new JwtAuthFilter();
    }

    /**
     * Создает и возвращает объект CorsConfigurationSource.
     *
     * @return объект CorsConfigurationSource для настройки CORS-конфигурации
     */
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        var config = new CorsConfiguration();
        config.setAllowedOrigins(List.of("*"));
        config.setAllowedMethods(List.of("*"));
        config.setAllowedHeaders(List.of("*"));

        var source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);

        return source;
    }
}
