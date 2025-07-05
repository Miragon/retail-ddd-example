package io.miragon.shop.adapter.inbound.mcp

import io.miragon.shop.application.port.inbound.ArticleQuery
import io.miragon.shop.domain.article.Article
import io.miragon.shop.domain.article.ArticleId
import org.springframework.ai.tool.annotation.Tool
import org.springframework.ai.tool.annotation.ToolParam
import java.util.*

class ArticleTools(
    private val query: ArticleQuery
) {

    @Tool(description = "Retrieves a list of all articles in the shop")
    fun getArticles(): List<ArticleData> {
        val articles = query.loadAll()
        return articles.map { fromArticle(it) }
    }

    @Tool(description = "Retrieves an article by its ID")
    fun getArticleById(@ToolParam id: String): ArticleData {
        val article = query.load(ArticleId(id))
        return fromArticle(article)
    }

    data class ArticleData(
        val id: UUID,
        val name: String,
        val description: String,
        val price: Double
    )

    private fun fromArticle(article: Article) = ArticleData(
        id = article.id.value,
        name = article.name.value,
        description = article.description.value,
        price = article.price.value
    )
}