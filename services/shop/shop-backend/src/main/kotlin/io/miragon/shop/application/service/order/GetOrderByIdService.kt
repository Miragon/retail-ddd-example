package io.miragon.shop.application.service.order

import io.miragon.shop.application.port.inbound.OrderQuery
import io.miragon.shop.application.port.outbound.OrderRepository
import io.miragon.shop.domain.order.Order
import io.miragon.shop.domain.order.OrderId
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
class GetOrderByIdService(
    private val orderRepository: OrderRepository
) : OrderQuery {
    
    override fun getOrderById(orderId: OrderId): Order {
        val order = orderRepository.loadById(orderId)
        return order ?: throw IllegalArgumentException("Order not found: $orderId")
    }
}