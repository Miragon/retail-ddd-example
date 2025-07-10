package io.miragon.shop.domain.cart

import io.miragon.shop.domain.shared.UserId
import io.miragon.shop.domain.testUserId

fun testCart(
    userId: UserId = testUserId(),
    items: List<CartItem> = listOf(testCartItem(userId = userId))
): Cart = Cart(
    userId = userId,
    items = items
)

fun testEmptyCart(
    userId: UserId = testUserId()
): Cart = Cart(
    userId = userId,
    items = emptyList()
)