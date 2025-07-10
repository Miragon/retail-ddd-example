package io.miragon.shop.domain.order

import io.miragon.shop.domain.article.ArticleId
import io.miragon.shop.domain.article.ArticleName
import io.miragon.shop.domain.article.testArticle
import io.miragon.shop.domain.shared.Price
import io.miragon.shop.domain.shared.Quantity
import java.util.*

fun testOrderItem(
    id: OrderItemId = OrderItemId(UUID.fromString("aaaaaaaa-bbbb-cccc-dddd-eeeeeeeeeeee")),
    articleId: ArticleId = testArticle().id,
    articleName: ArticleName = ArticleName("Test Article"),
    quantity: Quantity = Quantity(2),
    pricePerItem: Price = Price(99.99)
) = OrderItem(
    id = id,
    articleId = articleId,
    articleName = articleName,
    quantity = quantity,
    pricePerItem = pricePerItem
)