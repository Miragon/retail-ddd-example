package io.miragon.shop.domain.cart

import io.miragon.shop.domain.article.ArticleId
import io.miragon.shop.domain.article.testArticle
import io.miragon.shop.domain.shared.Price
import io.miragon.shop.domain.shared.Quantity
import io.miragon.shop.domain.shared.UserId
import io.miragon.shop.domain.testUserId
import java.util.*

fun testCartItem(
    id: CartItemId = CartItemId(UUID.fromString("a1b2c3d4-e5f6-7890-abcd-ef1234567890")),
    userId: UserId = testUserId(),
    articleId: ArticleId = testArticle().id,
    quantity: Quantity = Quantity(2),
    pricePerItem: Price = Price(99.99)
): CartItem = CartItem(
    id = id,
    userId = userId,
    articleId = articleId,
    quantity = quantity,
    pricePerItem = pricePerItem
)