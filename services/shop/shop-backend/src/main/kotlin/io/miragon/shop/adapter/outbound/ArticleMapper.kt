package io.miragon.shop.adapter.outbound

import io.miragon.shop.domain.Article
import io.miragon.shop.domain.shared.ArticleDescription
import io.miragon.shop.domain.shared.ArticleId
import io.miragon.shop.domain.shared.ArticleName
import io.miragon.shop.domain.shared.Price

object ArticleMapper {
    fun ArticleEntity.toDomain(): Article = Article(
        id = ArticleId(id),
        name = ArticleName(name),
        description = ArticleDescription(description),
        price = Price(price)
    )

    fun ArticleEntity.Companion.toEntity(
        article: Article
    ): ArticleEntity = ArticleEntity(
        id = article.id.value,
        name = article.name.value,
        description = article.description.value,
        price = article.price.value
    )
} 