package io.miragon.shop.application.service.article

import io.github.oshai.kotlinlogging.KotlinLogging
import io.miragon.shop.application.port.inbound.ImportArticleUseCase
import io.miragon.shop.application.port.outbound.ArticleRepository
import io.miragon.shop.domain.article.Article
import jakarta.transaction.Transactional
import org.springframework.stereotype.Service

@Service
@Transactional
class ImportArticleService(
    private val articleRepository: ArticleRepository
) : ImportArticleUseCase {

    private val log = KotlinLogging.logger {}

    override fun importArticle(article: Article) {
        articleRepository.save(article)
        log.info { "Imported article ${article.id} with name '${article.name}' and price ${article.price}" }
    }
} 