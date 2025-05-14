package io.miragon.warehouse.application.port.outbound

import io.miragon.warehouse.domain.Article

interface ArticleRepository {
    fun loadAll(): List<Article>
}