package io.miragon.delivery.domain

import io.miragon.delivery.domain.shared.ArticleName

data class Article(
    val id: io.miragon.delivery.domain.shared.ArticleId,
    val name: ArticleName,
    val description: io.miragon.delivery.domain.shared.ArticleDescription,
    val price: io.miragon.delivery.domain.shared.Price,
)
