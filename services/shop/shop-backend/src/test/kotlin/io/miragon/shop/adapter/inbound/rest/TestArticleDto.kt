package io.miragon.shop.adapter.inbound.rest

import java.util.*

fun testArticleDto(
    id: UUID = UUID.fromString("4ca4a9fe-6ce6-41a1-87f6-f99d4c6882a4"),
    name: String = "Test Article",
    description: String = "Test Description",
    price: Double = 99.99
): ArticleDto = ArticleDto(
    id = id,
    name = name,
    description = description,
    price = price
) 