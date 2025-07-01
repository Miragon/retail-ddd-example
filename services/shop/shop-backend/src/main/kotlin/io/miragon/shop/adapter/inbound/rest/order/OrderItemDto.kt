package io.miragon.shop.adapter.inbound.rest.order

import io.miragon.shop.domain.order.OrderItem
import java.util.*

data class OrderItemDto(
    val articleId: UUID,
    val articleName: String,
    val quantity: Int,
    val pricePerItem: Double,
    val totalPrice: Double
) {
    companion object {
        fun fromDomain(orderItem: OrderItem) = OrderItemDto(
            articleId = orderItem.articleId.value,
            articleName = orderItem.articleName.value,
            quantity = orderItem.quantity.value,
            pricePerItem = orderItem.pricePerItem.value,
            totalPrice = orderItem.totalPrice().value
        )
    }
}