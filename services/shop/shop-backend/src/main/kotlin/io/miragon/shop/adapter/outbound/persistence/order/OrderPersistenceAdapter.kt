package io.miragon.shop.adapter.outbound.persistence.order

import io.miragon.shop.adapter.outbound.persistence.order.OrderMapper.toDomain
import io.miragon.shop.adapter.outbound.persistence.order.OrderMapper.toEntity
import io.miragon.shop.application.port.outbound.OrderRepository
import io.miragon.shop.domain.order.Order
import io.miragon.shop.domain.order.OrderId
import io.miragon.shop.domain.shared.UserId
import org.springframework.stereotype.Component

@Component
class OrderPersistenceAdapter(
    private val orderJpaRepository: OrderJpaRepository
) : OrderRepository {

    override fun save(order: Order): Order {
        val entity = OrderEntity.Companion.toEntity(order)
        val savedEntity = orderJpaRepository.save(entity)
        return savedEntity.toDomain()
    }

    override fun loadById(orderId: OrderId): Order? {
        return orderJpaRepository.findById(orderId.value)
            .map { it.toDomain() }
            .orElse(null)
    }

    override fun loadById(userId: UserId): List<Order> {
        return orderJpaRepository.findByUserId(userId.value)
            .map { it.toDomain() }
    }
}