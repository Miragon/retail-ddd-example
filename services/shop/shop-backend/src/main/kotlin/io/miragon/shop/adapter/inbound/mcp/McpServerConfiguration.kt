package io.miragon.shop.adapter.inbound.mcp

import io.miragon.shop.application.port.inbound.ArticleQuery
import org.springframework.ai.tool.method.MethodToolCallbackProvider
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Profile

@Configuration
@Profile("mcp-server")
class McpServerConfiguration {

    @Bean
    fun articleTools(query: ArticleQuery): ArticleTools {
        return ArticleTools(query)
    }

    @Bean
    fun registerMcpTools(articleTools: ArticleTools): MethodToolCallbackProvider {
        return MethodToolCallbackProvider
            .builder()
            .toolObjects(articleTools)
            .build()
    }
}