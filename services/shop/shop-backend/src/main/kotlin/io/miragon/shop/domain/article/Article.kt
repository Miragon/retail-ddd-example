package io.miragon.shop.domain.article

import io.miragon.shop.domain.shared.Price

data class Article(
    val id: ArticleId,
    val name: ArticleName,
    val description: ArticleDescription,
    val price: Price,
)
