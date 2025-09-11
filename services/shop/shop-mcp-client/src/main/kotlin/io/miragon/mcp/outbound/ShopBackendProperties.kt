package io.miragon.mcp.outbound

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties(prefix = "shop.backend")
data class ShopBackendProperties(
    val url: String = "http://localhost:8081"
)