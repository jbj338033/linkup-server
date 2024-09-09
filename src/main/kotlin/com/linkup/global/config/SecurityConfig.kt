package com.linkup.global.config

import com.linkup.global.security.jwt.filter.JwtAuthenticationFilter
import com.linkup.global.security.jwt.filter.JwtExceptionFilter
import com.linkup.global.security.jwt.handler.JwtAccessDeniedHandler
import com.linkup.global.security.jwt.handler.JwtAuthenticationEntryPoint
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
    fun filterChain(
        http: HttpSecurity, jwtAccessDeniedHandler: JwtAccessDeniedHandler,
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

        .authorizeHttpRequests {
            it
                .requestMatchers(HttpMethod.GET, "/swagger-ui/**", "/v3/api-docs/**").permitAll()

                .requestMatchers(HttpMethod.POST, "/auth/login", "/auth/signup", "/auth/reissue").anonymous()
                .requestMatchers(HttpMethod.GET, "/auth/check").permitAll()

                .requestMatchers(HttpMethod.GET, "/users").authenticated()

                .requestMatchers(HttpMethod.POST, "/files/upload").permitAll()

                .requestMatchers(HttpMethod.GET, "/friends").authenticated()
                .requestMatchers(HttpMethod.DELETE, "/friends/{friendId}").authenticated()

                .requestMatchers(HttpMethod.GET, "/friend-requests", "/friend-requests/sent").authenticated()
                .requestMatchers(
                    HttpMethod.POST,
                    "/friend-requests",
                    "/friend-requests/accept",
                    "/friend-requests/reject",
                    "/friend-requests/cancel"
                ).authenticated()

                .requestMatchers(HttpMethod.GET, "/chat-rooms").authenticated()

                .requestMatchers(HttpMethod.GET, "/chat-rooms/open").authenticated()
                .requestMatchers(
                    HttpMethod.POST,
                    "/chat-rooms/open",
                    "/chat-rooms/open/{chatRoomId}/join",
                    "/chat-rooms/open/{chatRoomId}/leave"
                ).authenticated()

                .requestMatchers(HttpMethod.GET, "/chat-rooms/personal").authenticated()
                .requestMatchers(HttpMethod.POST, "/chat-rooms/personal").authenticated()

                .requestMatchers(HttpMethod.GET, "/chat-rooms/group").authenticated()
                .requestMatchers(HttpMethod.POST, "/chat-rooms/group").authenticated()

                .requestMatchers(HttpMethod.GET, "/uploads/**").permitAll()

                .requestMatchers(HttpMethod.GET, "/chat-messages/general").permitAll()

                .anyRequest().permitAll()
//                .anyRequest().authenticated()
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
        })
    }
}