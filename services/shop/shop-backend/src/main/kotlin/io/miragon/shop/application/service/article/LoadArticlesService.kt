package io.miragon.shop.application.service.article

import io.miragon.shop.application.port.inbound.ArticleQuery
import io.miragon.shop.application.port.outbound.ArticleRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
class LoadArticlesService(
    private val repository: ArticleRepository,
) : ArticleQuery {
    override fun loadAll() = repository.loadAll()
}