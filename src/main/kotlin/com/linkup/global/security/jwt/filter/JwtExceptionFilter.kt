package com.linkup.global.security.jwt.filter

import com.fasterxml.jackson.databind.ObjectMapper
import com.linkup.global.error.CustomException
import com.linkup.global.error.ErrorResponse
import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter

@Component
class JwtExceptionFilter(
    private val objectMapper: ObjectMapper
) : OncePerRequestFilter() {
    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        try {
            filterChain.doFilter(request, response)
        } catch (e: CustomException) {
            response.sendError(e)
        }
    }

    private fun HttpServletResponse.sendError(exception: CustomException) {
        val error = exception.error

        status = error.status.value()

        outputStream.use {
            it.write(objectMapper.writeValueAsBytes(ErrorResponse.of(exception)))
            it.flush()
        }
    }
}