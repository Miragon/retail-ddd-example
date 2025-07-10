package io.miragon.shop.application.port.inbound

import io.miragon.shop.domain.order.Order
import io.miragon.shop.domain.shared.UserId

interface PlaceOrderUseCase {
    fun placeOrder(userId: UserId): Order
}