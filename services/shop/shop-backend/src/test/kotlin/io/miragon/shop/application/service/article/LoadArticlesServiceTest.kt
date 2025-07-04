package io.miragon.shop.application.service.article

import io.miragon.shop.application.port.outbound.ArticleRepository
import io.miragon.shop.domain.article.testArticle
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

        // given
        val articles = listOf(testArticle(name = "Article 1"), testArticle(name = "Article 2"))
        every { articleRepository.loadAll() } returns articles

        // when
        val result = service.loadAll()

        // then
        val expectedArticles = listOf(testArticle(name = "Article 1"), testArticle(name = "Article 2"))
        assertThat(result).usingRecursiveComparison().isEqualTo(expectedArticles)
        verify(exactly = 1) { articleRepository.loadAll() }
        confirmVerified(articleRepository)
    }
} 