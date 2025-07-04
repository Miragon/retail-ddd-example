package io.miragon.shop.application.port.inbound

import io.miragon.shop.domain.order.Order
import io.miragon.shop.domain.order.OrderId

interface OrderQuery {
    fun getOrderById(orderId: OrderId): Order
}