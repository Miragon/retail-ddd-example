package io.miragon.shop.application.port.outbound

import io.miragon.shop.domain.order.Order
import io.miragon.shop.domain.order.OrderId
import io.miragon.shop.domain.shared.UserId

interface OrderRepository {
    fun save(order: Order): Order
    fun loadById(orderId: OrderId): Order?
    fun loadById(userId: UserId): List<Order>
}