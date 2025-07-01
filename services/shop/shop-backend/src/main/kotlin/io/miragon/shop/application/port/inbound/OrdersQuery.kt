package io.miragon.shop.application.port.inbound

import io.miragon.shop.domain.order.Order
import io.miragon.shop.domain.shared.UserId

interface OrdersQuery {
    fun getOrders(userId: UserId): List<Order>
}