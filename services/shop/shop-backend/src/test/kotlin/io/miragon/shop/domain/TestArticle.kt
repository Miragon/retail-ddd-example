package io.miragon.shop.domain

import io.miragon.shop.domain.shared.ArticleDescription
import io.miragon.shop.domain.shared.ArticleId
import io.miragon.shop.domain.shared.ArticleName
import io.miragon.shop.domain.shared.Price
import java.util.*

fun testArticle(
    id: UUID = UUID.fromString("4ca4a9fe-6ce6-41a1-87f6-f99d4c6882a4"),
    name: String = "Test Article",
    description: String = "Test Description",
    price: Double = 99.99
): Article = Article(
    id = ArticleId(id),
    name = ArticleName(name),
    description = ArticleDescription(description),
    price = Price(price)
) 