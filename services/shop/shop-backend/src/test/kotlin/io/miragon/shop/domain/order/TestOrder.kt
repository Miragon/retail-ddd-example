package io.miragon.shop.domain.order

import io.miragon.shop.domain.shared.Price
import io.miragon.shop.domain.shared.UserId
import io.miragon.shop.domain.testUserId
import java.time.LocalDateTime
import java.util.*

fun testOrder(
    id: OrderId = OrderId(UUID.fromString("11111111-2222-3333-4444-555555555555")),
    userId: UserId = testUserId(),
    items: List<OrderItem> = listOf(testOrderItem()),
    status: OrderStatus = OrderStatus.PLACED,
    orderDate: LocalDateTime = LocalDateTime.of(2024, 1, 15, 10, 30),
    totalAmount: Price = Price(199.98)
): Order = Order(
    id = id,
    userId = userId,
    items = items,
    status = status,
    orderDate = orderDate,
    totalAmount = totalAmount
)