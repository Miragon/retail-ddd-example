package io.miragon.shop.application.port.inbound

import io.miragon.shop.domain.cart.Cart
import io.miragon.shop.domain.shared.UserId

interface CartQuery {
    fun getCart(userId: UserId): Cart
}