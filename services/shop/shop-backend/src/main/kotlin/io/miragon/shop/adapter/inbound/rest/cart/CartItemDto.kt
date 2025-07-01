package io.miragon.shop.adapter.inbound.rest.cart

import io.miragon.shop.domain.cart.CartItem
import java.util.*

data class CartItemDto(
    val articleId: UUID,
    val quantity: Int,
    val pricePerItem: Double,
    val totalPrice: Double
) {
    companion object {
        fun fromDomain(cartItem: CartItem) = CartItemDto(
            articleId = cartItem.articleId.value,
            quantity = cartItem.quantity.value,
            pricePerItem = cartItem.pricePerItem.value,
            totalPrice = cartItem.totalPrice().value
        )
    }
}