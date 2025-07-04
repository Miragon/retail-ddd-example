package io.miragon.shop.adapter.outbound.persistence.order

import io.miragon.shop.domain.order.OrderStatus
import jakarta.persistence.CascadeType
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.FetchType
import jakarta.persistence.Id
import jakarta.persistence.OneToMany
import java.time.LocalDateTime
import java.util.*

@Entity(name = "orders")
class OrderEntity(
    @Id
    @Column(name = "id", nullable = false)
    val id: UUID,

    @Column(name = "user_id", nullable = false)
    val userId: String,

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    val status: OrderStatus,

    @Column(name = "order_date", nullable = false)
    val orderDate: LocalDateTime,

    @Column(name = "total_amount", nullable = false)
    val totalAmount: Double,

    @OneToMany(mappedBy = "orderId", cascade = [CascadeType.ALL], fetch = FetchType.LAZY, orphanRemoval = true)
    val items: List<OrderItemEntity>
) {
    companion object
}
