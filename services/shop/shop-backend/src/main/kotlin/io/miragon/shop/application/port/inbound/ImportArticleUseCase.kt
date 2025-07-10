package io.miragon.shop.application.port.inbound

import io.miragon.shop.domain.article.Article

interface ImportArticleUseCase {
    fun importArticle(article: Article)
} 