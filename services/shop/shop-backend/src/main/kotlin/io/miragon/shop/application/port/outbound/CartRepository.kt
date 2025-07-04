package io.miragon.shop.application.port.outbound

import io.miragon.shop.domain.cart.Cart
import io.miragon.shop.domain.shared.UserId

interface CartRepository {
    fun loadCart(userId: UserId): Cart
    fun storeCart(cart: Cart)
    fun deleteCart(userId: UserId)
}