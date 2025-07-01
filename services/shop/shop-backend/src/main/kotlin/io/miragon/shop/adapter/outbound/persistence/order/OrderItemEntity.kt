package io.miragon.shop.adapter.outbound.persistence.order

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import java.util.*

@Entity(name = "order_items")
class OrderItemEntity(

    @Id
    @Column(name = "id", nullable = false)
    val id: UUID,

    @Column(name = "order_id", nullable = false)
    val orderId: UUID,

    @Column(name = "article_id", nullable = false)
    val articleId: UUID,

    @Column(name = "article_name", nullable = false)
    val articleName: String,

    @Column(name = "quantity", nullable = false)
    val quantity: Int,

    @Column(name = "price_per_item", nullable = false)
    val pricePerItem: Double
) {
    companion object
}