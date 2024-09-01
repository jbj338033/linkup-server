package com.kakaotalk.global.security.jwt.provider

import com.kakaotalk.domain.user.domain.entity.User
import com.kakaotalk.domain.user.error.UserError
import com.kakaotalk.domain.user.repository.UserRepository
import com.kakaotalk.global.error.CustomException
import com.kakaotalk.global.security.details.CustomUserDetails
import com.kakaotalk.global.security.jwt.config.JwtProperties
import com.kakaotalk.global.security.jwt.dto.JwtResponse
import com.kakaotalk.global.security.jwt.enums.JwtType
import com.kakaotalk.global.security.jwt.error.JwtError
import io.jsonwebtoken.*
import jakarta.servlet.http.HttpServletRequest
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.stereotype.Component
import java.nio.charset.StandardCharsets
import java.util.Date
import java.util.concurrent.TimeUnit
import javax.crypto.spec.SecretKeySpec

@Component
class JwtProvider(
    private val jwtProperties: JwtProperties,
    private val redisTemplate: RedisTemplate<String, String>,
    private val userRepository: UserRepository
) {
    private val key = SecretKeySpec(jwtProperties.secretKey.toByteArray(StandardCharsets.UTF_8), Jwts.SIG.HS512.key().build().algorithm)

    fun generateToken(user: User): JwtResponse {
        val now = Date()

        val accessToken = Jwts.builder()
            .header()
            .type(JwtType.ACCESS.name)
            .and()
            .subject(user.email)
            .issuedAt(now)
            .issuer(jwtProperties.issuer)
            .expiration(Date(now.time + jwtProperties.accessTokenExpiration))
            .signWith(key)
            .compact()

        val refreshToken = Jwts.builder()
            .header()
            .type(JwtType.REFRESH.name)
            .and()
            .subject(user.email)
            .issuedAt(now)
            .issuer(jwtProperties.issuer)
            .expiration(Date(now.time + jwtProperties.refreshTokenExpiration))
            .signWith(key)
            .compact()

        redisTemplate.opsForValue().set("refreshToken:${user.email}", refreshToken, jwtProperties.refreshTokenExpiration, TimeUnit.MILLISECONDS)

        return JwtResponse(accessToken, refreshToken)
    }

    fun getEmail(token: String) = getClaims(token).subject

    fun getAuthentication(token: String): Authentication {
        val claims = getClaims(token)
        val user = userRepository.findByEmail(claims.subject) ?: throw CustomException(UserError.USER_NOT_FOUND)
        val details = CustomUserDetails(user)

        return UsernamePasswordAuthenticationToken(details, null, details.authorities)
    }

    fun extractToken(request: HttpServletRequest) = request.getHeader(jwtProperties.header)?.removePrefix(jwtProperties.prefix)

    fun getType(token: String) = JwtType.valueOf(Jwts.parser().verifyWith(key).requireIssuer(jwtProperties.issuer).build().parseSignedClaims(token).header.type)

    private fun getClaims(token: String): Claims {
        try {
            return Jwts.parser()
                .verifyWith(key)
                .requireIssuer(jwtProperties.issuer)
                .build()
                .parseSignedClaims(token)
                .payload
        } catch (e: ExpiredJwtException) {
            throw CustomException(JwtError.EXPIRED_TOKEN)
        } catch (e: UnsupportedJwtException) {
            throw CustomException(JwtError.UNSUPPORTED_TOKEN)
        } catch (e: MalformedJwtException) {
            throw CustomException(JwtError.MALFORMED_TOKEN)
        } catch (e: SecurityException) {
            throw CustomException(JwtError.INVALID_TOKEN)
        } catch (e: IllegalArgumentException) {
            throw CustomException(JwtError.INVALID_TOKEN)
        }
    }
}