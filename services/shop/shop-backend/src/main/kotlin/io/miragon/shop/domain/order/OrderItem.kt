package io.miragon.shop.domain.order

import io.miragon.shop.domain.article.ArticleId
import io.miragon.shop.domain.article.ArticleName
import io.miragon.shop.domain.shared.Price
import io.miragon.shop.domain.shared.Quantity

data class OrderItem(
    val id: OrderItemId,
    val articleId: ArticleId,
    val articleName: ArticleName,
    val quantity: Quantity,
    val pricePerItem: Price
) {
    fun totalPrice(): Price = Price(pricePerItem.value * quantity.value)
}