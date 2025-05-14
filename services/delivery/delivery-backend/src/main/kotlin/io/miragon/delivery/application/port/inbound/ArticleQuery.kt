package io.miragon.delivery.application.port.inbound

interface ArticleQuery {
    fun loadAll(): List<io.miragon.delivery.domain.Article>
}