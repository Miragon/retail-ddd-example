package io.miragon.shop.application.service

import io.miragon.shop.application.port.inbound.ArticleQuery
import io.miragon.shop.application.port.outbound.ArticleRepository
import org.springframework.stereotype.Service

@Service
class LoadArticlesService(
    private val repository: ArticleRepository,
) : ArticleQuery {
    override fun loadAll() = repository.loadAll()
}