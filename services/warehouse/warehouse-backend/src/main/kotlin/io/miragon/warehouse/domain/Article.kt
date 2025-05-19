package io.miragon.warehouse.domain

import io.miragon.warehouse.domain.shared.ArticleDescription
import io.miragon.warehouse.domain.shared.ArticleId
import io.miragon.warehouse.domain.shared.ArticleName
import io.miragon.warehouse.domain.shared.Price

data class Article(
    val id: ArticleId,
    val name: ArticleName,
    val description: ArticleDescription,
    val price: Price,
)
