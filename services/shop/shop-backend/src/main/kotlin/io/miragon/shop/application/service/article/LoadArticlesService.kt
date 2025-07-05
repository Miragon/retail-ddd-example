package io.miragon.shop.application.service.article

import io.miragon.shop.application.port.inbound.ArticleQuery
import io.miragon.shop.application.port.outbound.ArticleRepository
import io.miragon.shop.domain.article.Article
import io.miragon.shop.domain.article.ArticleId
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
class LoadArticlesService(
    private val repository: ArticleRepository,
) : ArticleQuery {

    override fun loadAll() = repository.loadAll()
        
    override fun load(id: ArticleId): Article {
        val article = repository.findById(id)
        return article ?: throw IllegalArgumentException("Article with id ${id.value} not found")
    }
}