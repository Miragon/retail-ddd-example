package io.miragon.delivery.application.port.outbound

interface ArticleRepository {
    fun loadAll(): List<io.miragon.delivery.domain.Article>
}