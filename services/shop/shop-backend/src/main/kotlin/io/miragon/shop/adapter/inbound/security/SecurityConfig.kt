package io.miragon.shop.adapter.inbound.security

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter
import org.springframework.security.web.SecurityFilterChain
import org.springframework.web.cors.CorsConfiguration
import org.springframework.web.cors.CorsConfigurationSource
import org.springframework.web.cors.UrlBasedCorsConfigurationSource

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(securedEnabled = true, prePostEnabled = true, jsr250Enabled = true)
class SecurityConfig(
    @param:Value("\${spring.security.allowed-cors-origins:}")
    private val allowedCorsOrigins: String
) {
    private val configuredCorsOrigins = allowedCorsOrigins
        .split(",")
        .map { it.trim() }
        .filter { it.isNotBlank() }

    @Bean
    @Throws(Exception::class)
    fun securityFilterChain(http: HttpSecurity): SecurityFilterChain {
        val httpSecurity = http.authorizeHttpRequests { auth ->
            auth.requestMatchers(
                "/actuator/**",
                "/v3/api-docs/**",
                "/swagger-ui/**",
                "/api/articles/**"
            ).permitAll().anyRequest().authenticated()
        }

        if (configuredCorsOrigins.isNotEmpty()) {
            httpSecurity.cors { cors -> cors.configurationSource(corsConfigurationSource()) }
        }

        httpSecurity.oauth2ResourceServer { oauth2 ->
            oauth2.jwt { jwt -> jwt.jwtAuthenticationConverter(jwtAuthenticationConverter()) }
        }.headers { it.frameOptions { options -> options.sameOrigin() } }
            .csrf { it.disable() }
            .sessionManagement { it.sessionCreationPolicy(SessionCreationPolicy.STATELESS) }

        return http.build()
    }

    private fun jwtAuthenticationConverter(): JwtAuthenticationConverter {
        val customRoleConverter = JwtRolesConverter()
        val jwtAuthenticationConverter = JwtAuthenticationConverter()
        jwtAuthenticationConverter.setJwtGrantedAuthoritiesConverter(customRoleConverter)
        return jwtAuthenticationConverter
    }

    private fun corsConfigurationSource(): CorsConfigurationSource {
        val source = UrlBasedCorsConfigurationSource()
        val corsConfiguration = CorsConfiguration().apply {
            allowedOrigins = configuredCorsOrigins
            allowedMethods = listOf("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS")
            allowedHeaders = listOf("Authorization", "Content-Type", "Accept", "Origin")
            maxAge = 3600
        }
        source.registerCorsConfiguration("/**", corsConfiguration)
        return source
    }
}
