package io.miragon.shop.application.service.order

import io.miragon.shop.application.port.inbound.OrdersQuery
import io.miragon.shop.application.port.outbound.OrderRepository
import io.miragon.shop.domain.order.Order
import io.miragon.shop.domain.shared.UserId
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
class GetOrdersService(
    private val orderRepository: OrderRepository
) : OrdersQuery {

    override fun getOrders(userId: UserId): List<Order> {
        return orderRepository.loadById(userId)
    }
}