package io.miragon.shop.domain.article

import io.miragon.shop.domain.shared.Price

fun testArticle(
    id: ArticleId = ArticleId("4ca4a9fe-6ce6-41a1-87f6-f99d4c6882a4"),
    name: String = "Test Article",
    description: String = "Test Description",
    price: Double = 99.99
): Article = Article(
    id = id,
    name = ArticleName(name),
    description = ArticleDescription(description),
    price = Price(price)
) 