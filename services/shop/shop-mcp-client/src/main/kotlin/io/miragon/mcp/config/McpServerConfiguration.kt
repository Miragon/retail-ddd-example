package io.miragon.mcp.config

import io.miragon.mcp.inbound.ArticleTools
import io.miragon.mcp.outbound.ShopApiClient
import io.miragon.mcp.outbound.ShopBackendProperties
import org.springframework.ai.tool.method.MethodToolCallbackProvider
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.client.RestTemplate

@Configuration
@EnableConfigurationProperties(ShopBackendProperties::class)
class McpServerConfiguration {

    @Bean
    fun restTemplate(): RestTemplate {
        return RestTemplate()
    }

    @Bean
    fun shopApiClient(
        restTemplate: RestTemplate,
        shopBackendProperties: ShopBackendProperties
    ): ShopApiClient {
        return ShopApiClient(restTemplate, shopBackendProperties)
    }

    @Bean
    fun articleTools(shopApiClient: ShopApiClient): ArticleTools {
        return ArticleTools(shopApiClient)
    }

    @Bean
    fun registerMcpTools(articleTools: ArticleTools): MethodToolCallbackProvider {
        return MethodToolCallbackProvider
            .builder()
            .toolObjects(articleTools)
            .build()
    }
}