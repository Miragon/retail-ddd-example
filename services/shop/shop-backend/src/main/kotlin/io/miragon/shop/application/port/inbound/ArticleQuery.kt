package io.miragon.shop.application.port.inbound

import io.miragon.shop.domain.Article

interface ArticleQuery {
    fun loadAll(): List<Article>
}