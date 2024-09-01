package com.kakaotalk.global.config

import com.kakaotalk.global.security.jwt.filter.JwtAuthenticationFilter
import com.kakaotalk.global.security.jwt.filter.JwtExceptionFilter
import com.kakaotalk.global.security.jwt.handler.JwtAccessDeniedHandler
import com.kakaotalk.global.security.jwt.handler.JwtAuthenticationEntryPoint
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpMethod
import org.springframework.security.access.hierarchicalroles.RoleHierarchy
import org.springframework.security.access.hierarchicalroles.RoleHierarchyImpl
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import org.springframework.web.cors.CorsConfiguration
import org.springframework.web.cors.CorsConfigurationSource
import org.springframework.web.cors.UrlBasedCorsConfigurationSource

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
class SecurityConfig {
    @Bean
    fun passwordEncoder() = BCryptPasswordEncoder()

    @Bean
    fun roleHierarchy(): RoleHierarchy = RoleHierarchyImpl.fromHierarchy("ROLE_ADMIN > ROLE_USER")

    @Bean
    fun filterChain(http: HttpSecurity, jwtAccessDeniedHandler: JwtAccessDeniedHandler,
                    jwtAuthenticationEntryPoint: JwtAuthenticationEntryPoint,
                    jwtAuthenticationFilter: JwtAuthenticationFilter, jwtExceptionFilter: JwtExceptionFilter
    ): SecurityFilterChain = http
        .csrf { it.disable() }
        .cors { it.configurationSource(corsConfigurationSource()) }
        .formLogin { it.disable() }
        .httpBasic { it.disable() }
        .rememberMe { it.disable() }
        .logout { it.disable() }

        .exceptionHandling {
            it.accessDeniedHandler(jwtAccessDeniedHandler)
            it.authenticationEntryPoint(jwtAuthenticationEntryPoint)
        }

        .sessionManagement {
            it.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        }

        .authorizeHttpRequests { it
            .requestMatchers(HttpMethod.GET, "/swagger-ui/**", "/v3/api-docs/**").permitAll()
            .requestMatchers(HttpMethod.POST, "/auth/login", "/auth/signup", "/auth/reissue").anonymous()
            .requestMatchers(HttpMethod.GET, "/auth/email", "/auth/phone-number").permitAll()
            .requestMatchers(HttpMethod.GET, "/users/me").authenticated()
            .anyRequest().authenticated()
        }

        .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter::class.java)
        .addFilterBefore(jwtExceptionFilter, jwtAuthenticationFilter::class.java)

        .build()

    @Bean
    fun corsConfigurationSource(): CorsConfigurationSource = UrlBasedCorsConfigurationSource().apply {
        registerCorsConfiguration("/**", CorsConfiguration().apply {
        allowedOriginPatterns = listOf("*")
        allowedMethods = listOf("GET", "POST", "PUT", "DELETE")
        allowedHeaders = listOf("*")
        allowCredentials = true
        maxAge = 3600
    })}
}