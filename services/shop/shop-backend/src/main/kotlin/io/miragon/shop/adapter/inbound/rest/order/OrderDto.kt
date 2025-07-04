package io.miragon.shop.adapter.inbound.rest.order

import io.miragon.shop.domain.order.Order
import java.time.LocalDateTime
import java.util.*

data class OrderDto(
    val id: UUID,
    val userId: String,
    val items: List<OrderItemDto>,
    val status: String,
    val orderDate: LocalDateTime,
    val totalAmount: Double
) {
    companion object {
        fun fromDomain(order: Order) = OrderDto(
            id = order.id.value,
            userId = order.userId.value,
            status = order.status.name,
            orderDate = order.orderDate,
            totalAmount = order.totalAmount.value,
            items = order.items.map { OrderItemDto.fromDomain(it) },
        )
    }
}