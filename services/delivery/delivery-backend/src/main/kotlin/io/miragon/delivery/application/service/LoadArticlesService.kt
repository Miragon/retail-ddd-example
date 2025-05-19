package io.miragon.delivery.application.service

import org.springframework.stereotype.Service

@Service
class LoadArticlesService(
    private val repository: io.miragon.delivery.application.port.outbound.ArticleRepository
) : io.miragon.delivery.application.port.inbound.ArticleQuery {
    override fun loadAll() = repository.loadAll()
}