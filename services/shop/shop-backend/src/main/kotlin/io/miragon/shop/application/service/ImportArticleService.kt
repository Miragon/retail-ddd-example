package io.miragon.shop.application.service

import io.miragon.shop.application.port.inbound.ImportArticleUseCase
import io.miragon.shop.application.port.outbound.ArticleRepository
import io.miragon.shop.domain.Article
import org.springframework.stereotype.Service
import jakarta.transaction.Transactional

@Service
@Transactional
class ImportArticleService(
    private val articleRepository: ArticleRepository
) : ImportArticleUseCase {

    override fun importArticle(article: Article) {
        articleRepository.save(article)
    }
} 