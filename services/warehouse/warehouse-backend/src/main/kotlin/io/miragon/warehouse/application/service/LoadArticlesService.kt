package io.miragon.warehouse.application.service

import io.miragon.warehouse.application.port.inbound.ArticleQuery
import io.miragon.warehouse.application.port.outbound.ArticleRepository
import org.springframework.stereotype.Service

@Service
class LoadArticlesService(
    private val repository: ArticleRepository
) : ArticleQuery {
    override fun loadAll() = repository.loadAll()
}