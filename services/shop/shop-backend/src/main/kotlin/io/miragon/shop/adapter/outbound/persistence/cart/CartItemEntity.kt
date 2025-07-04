package io.miragon.shop.adapter.outbound.persistence.cart

import jakarta.persistence.*
import java.util.*

@Entity(name = "cart_items")
class CartItemEntity(

    @Id
    @Column(name = "id", nullable = false)
    val id: UUID,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    val cart: CartEntity,

    @Column(name = "article_id", nullable = false)
    val articleId: UUID,

    @Column(name = "quantity", nullable = false)
    val quantity: Int,

    @Column(name = "price_per_item", nullable = false)
    val pricePerItem: Double
) {
    companion object
}