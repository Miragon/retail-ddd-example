package io.miragon.shop.application.port.inbound

import io.miragon.shop.domain.article.Article
import io.miragon.shop.domain.article.ArticleId

interface ArticleQuery {
    fun load(id: ArticleId): Article
    fun loadAll(): List<Article>
}