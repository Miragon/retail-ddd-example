package io.miragon.shop.application.service.article

import io.miragon.shop.application.port.outbound.ArticleRepository
import io.miragon.shop.domain.article.testArticle
import io.mockk.confirmVerified
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Test

class ImportArticleServiceTest {

    private val articleRepository = mockk<ArticleRepository>(relaxed = true)
    private val service = ImportArticleService(articleRepository)

    @Test
    fun `should save article when importing`() {
        val article = testArticle()
        service.importArticle(article)
        verify { articleRepository.save(testArticle()) }
        confirmVerified(articleRepository)
    }
} 
