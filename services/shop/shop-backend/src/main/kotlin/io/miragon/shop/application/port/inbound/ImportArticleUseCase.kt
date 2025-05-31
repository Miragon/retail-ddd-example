package io.miragon.shop.application.port.inbound

import io.miragon.shop.domain.Article

interface ImportArticleUseCase {
    fun importArticle(article: Article)
} 