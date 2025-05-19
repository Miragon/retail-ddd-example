package io.miragon.shop.domain

import io.miragon.shop.domain.shared.ArticleDescription
import io.miragon.shop.domain.shared.ArticleId
import io.miragon.shop.domain.shared.ArticleName
import io.miragon.shop.domain.shared.Price

data class Article(
    val id: ArticleId,
    val name: ArticleName,
    val description: ArticleDescription,
    val price: Price,
)
