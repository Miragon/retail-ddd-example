package io.miragon.shop.adapter.inbound.mcp

import io.miragon.shop.application.port.inbound.ArticleQuery
import io.miragon.shop.domain.article.testArticle
import io.mockk.every
import io.mockk.mockk
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class ArticleToolsTest {

    private val query = mockk<ArticleQuery>()
    private val underTest = ArticleTools(
        query = query
    )

    @Test
    fun `load articles`() {
        val article = testArticle()
        every { query.loadAll() } returns listOf(article)
        val articles = underTest.getArticles()
        assertThat(articles).containsExactly(
            ArticleTools.ArticleData(
                id = article.id.value,
                name = article.name.value,
                description = article.description.value,
                price = article.price.value
            )
        )
    }

    @Test
    fun `load article by id`() {
        val article = testArticle()
        every { query.load(article.id) } returns article
        val loadedArticle = underTest.getArticleById(article.id.value.toString())
        assertThat(loadedArticle).isEqualTo(
            ArticleTools.ArticleData(
                id = article.id.value,
                name = article.name.value,
                description = article.description.value,
                price = article.price.value
            )
        )
    }


}