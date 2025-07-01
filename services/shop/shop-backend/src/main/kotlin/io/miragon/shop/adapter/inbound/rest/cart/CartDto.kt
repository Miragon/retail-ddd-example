package io.miragon.shop.adapter.inbound.rest.cart

import io.miragon.shop.domain.cart.Cart

data class CartDto(
    val items: List<CartItemDto>,
    val totalPrice: Double
) {
    companion object {
        fun fromDomain(cart: Cart) = CartDto(
            items = cart.items.map { CartItemDto.fromDomain(it) },
            totalPrice = cart.totalPrice().value
        )
    }
}