package io.miragon.shop.adapter.outbound.persistence.order

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface OrderJpaRepository : JpaRepository<OrderEntity, UUID> {
    fun findByUserId(userId: String): List<OrderEntity>
}