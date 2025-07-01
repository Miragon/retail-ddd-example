package io.miragon.shop.adapter.outbound.persistence.article

import io.miragon.shop.domain.article.Article
import io.miragon.shop.domain.article.ArticleDescription
import io.miragon.shop.domain.article.ArticleId
import io.miragon.shop.domain.article.ArticleName
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