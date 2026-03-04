package io.miragon.shop.adapter.inbound.security

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.cors.CorsConfiguration
import org.springframework.web.cors.CorsConfigurationSource
import org.springframework.web.cors.UrlBasedCorsConfigurationSource

@Configuration
class CorsConfig(
    @param:Value("\${spring.security.allowed-cors-origins:}")
    private val allowedCorsOrigins: String
) {

    @Bean
    fun corsConfigurationSource(): CorsConfigurationSource {
        val configuredOrigins = allowedCorsOrigins.split(",").map { it.trim() }.filter { it.isNotBlank() }
        val source = UrlBasedCorsConfigurationSource()

        if (configuredOrigins.isNotEmpty()) {
            val corsConfiguration = CorsConfiguration().apply {
                allowedOrigins = configuredOrigins
                allowedMethods = listOf("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS")
                allowedHeaders = listOf("Authorization", "Content-Type", "Accept", "Origin")
                maxAge = 3600
            }
            source.registerCorsConfiguration("/**", corsConfiguration)
        }

        return source
    }
}
