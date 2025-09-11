package io.miragon.mcp.inbound

import io.miragon.mcp.outbound.ShopApiClient
import io.miragon.mcp.shared.ArticleData
import org.springframework.ai.tool.annotation.Tool
import org.springframework.ai.tool.annotation.ToolParam

class ArticleTools(
    private val shopApiClient: ShopApiClient
) {

    @Tool(description = "Retrieves a list of all articles in the shop")
    fun getArticles(): List<ArticleData> {
        return shopApiClient.loadAll()
    }

    @Tool(description = "Retrieves an article by its ID")
    fun getArticleById(@ToolParam id: String): ArticleData {
        return shopApiClient.loadById(id)
    }

}
