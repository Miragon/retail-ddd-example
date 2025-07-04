package io.miragon.shop.application.port.outbound

import io.miragon.shop.domain.article.Article
import io.miragon.shop.domain.article.ArticleId

interface ArticleRepository {
    fun loadAll(): List<Article>
    fun loadByIds(articleIds: List<ArticleId>): List<Article>
    fun save(article: Article)
    fun findById(articleId: ArticleId): Article?
}