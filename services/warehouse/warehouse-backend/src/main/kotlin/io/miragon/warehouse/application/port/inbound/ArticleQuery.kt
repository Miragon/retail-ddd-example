package io.miragon.warehouse.application.port.inbound

import io.miragon.warehouse.domain.Article

interface ArticleQuery {
    fun loadAll(): List<Article>
}