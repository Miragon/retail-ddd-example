package io.miragon.shop.application.port.outbound

import io.miragon.shop.domain.Article

interface ArticleRepository {
    fun loadAll(): List<Article>
}