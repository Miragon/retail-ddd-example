package io.miragon.shop.adapter.outbound.persistence.cart

import io.miragon.shop.domain.article.ArticleId
import io.miragon.shop.domain.cart.Cart
import io.miragon.shop.domain.cart.CartItem
import io.miragon.shop.domain.cart.CartItemId
import io.miragon.shop.domain.shared.Price
import io.miragon.shop.domain.shared.Quantity
import io.miragon.shop.domain.shared.UserId

object CartMapper {

    fun CartEntity.toDomain() = Cart(
        userId = UserId(this.userId),
        items = this.items.map { it.toDomain() }
    )

    fun CartEntity.Companion.toEntity(cart: Cart): CartEntity {
        val cartEntity = CartEntity(userId = cart.userId.value)
        val itemEntities = cart.items.map { CartItemEntity.toEntity(it, cartEntity) }
        cartEntity.items.addAll(itemEntities)
        return cartEntity
    }

    fun CartItemEntity.toDomain() = CartItem(
        id = CartItemId(id),
        userId = UserId(this.cart.userId),
        articleId = ArticleId(this.articleId),
        quantity = Quantity(this.quantity),
        pricePerItem = Price(this.pricePerItem)
    )

    fun CartItemEntity.Companion.toEntity(
        cartItem: CartItem,
        cartEntity: CartEntity
    ) = CartItemEntity(
        id = cartItem.id.value,
        cart = cartEntity,
        articleId = cartItem.articleId.value,
        quantity = cartItem.quantity.value,
        pricePerItem = cartItem.pricePerItem.value
    )
}