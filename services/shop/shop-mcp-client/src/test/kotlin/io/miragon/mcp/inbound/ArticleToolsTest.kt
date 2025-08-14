package io.miragon.mcp.inbound

import io.miragon.mcp.outbound.ShopApiClient
import io.miragon.mcp.shared.ArticleData
import io.mockk.every
import io.mockk.mockk
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import java.util.*

class ArticleToolsTest {

    private val shopApiClient = mockk<ShopApiClient>()
    private val underTest = ArticleTools(shopApiClient)

    @Test
    fun `load articles`() {
        val articleData = ArticleData(
            id = UUID.randomUUID(),
            name = "Test Article",
            description = "Test Description",
            price = 9.99
        )
        every { shopApiClient.loadAll() } returns listOf(articleData)
        
        val articles = underTest.getArticles()
        
        assertThat(articles).containsExactly(articleData)
    }

    @Test
    fun `load article by id`() {
        val articleData = ArticleData(
            id = UUID.randomUUID(),
            name = "Test Article",
            description = "Test Description",
            price = 9.99
        )
        val idString = articleData.id.toString()
        every { shopApiClient.loadById(idString) } returns articleData
        
        val loadedArticle = underTest.getArticleById(idString)
        
        assertThat(loadedArticle).isEqualTo(articleData)
    }
}