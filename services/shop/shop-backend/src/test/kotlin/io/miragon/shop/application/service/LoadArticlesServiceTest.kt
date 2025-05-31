package io.miragon.shop.application.service

import io.miragon.shop.application.port.outbound.ArticleRepository
import io.miragon.shop.domain.testArticle
import io.mockk.confirmVerified
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class LoadArticlesServiceTest {

    private val articleRepository = mockk<ArticleRepository>()
    private val service = LoadArticlesService(articleRepository)

    @Test
    fun `should return all articles when loading`() {
        val articles = listOf(testArticle(name = "Article 1"), testArticle(name = "Article 2"))
        every { articleRepository.loadAll() } returns articles
        val result = service.loadAll()
        val expectedArticles = listOf(testArticle(name = "Article 1"), testArticle(name = "Article 2"))
        assertThat(result).usingRecursiveComparison().isEqualTo(expectedArticles)
        verify(exactly = 1) { articleRepository.loadAll() }
        confirmVerified(articleRepository)
    }
} 