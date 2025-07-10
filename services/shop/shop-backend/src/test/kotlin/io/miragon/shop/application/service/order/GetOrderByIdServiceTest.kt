package io.miragon.shop.application.service.order

import io.miragon.shop.application.port.outbound.OrderRepository
import io.miragon.shop.domain.order.OrderId
import io.miragon.shop.domain.order.testOrder
import io.miragon.shop.domain.shared.Price
import io.mockk.confirmVerified
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.jupiter.api.Test
import java.util.*

class GetOrderByIdServiceTest {

    private val orderRepository = mockk<OrderRepository>()
    private val service = GetOrderByIdService(orderRepository)

    @Test
    fun `should return order when order exists`() {
        // given
        val orderId = OrderId(UUID.randomUUID())
        val expectedOrder = testOrder(id = orderId)

        every { orderRepository.loadById(orderId) } returns expectedOrder

        // when
        val result = service.getOrderById(orderId)

        // then
        assertThat(result).usingRecursiveComparison().isEqualTo(expectedOrder)
        verify { orderRepository.loadById(orderId) }
        confirmVerified(orderRepository)
    }

    @Test
    fun `should throw exception when order does not exist`() {
        // given
        val orderId = OrderId(UUID.randomUUID())

        every { orderRepository.loadById(orderId) } returns null

        // when & then
        assertThatThrownBy { service.getOrderById(orderId) }
            .isInstanceOf(IllegalArgumentException::class.java)
            .hasMessage("Order not found: $orderId")

        verify { orderRepository.loadById(orderId) }
        confirmVerified(orderRepository)
    }

    @Test
    fun `should call repository with correct order id`() {
        // given
        val orderId = OrderId(UUID.fromString("12345678-1234-1234-1234-123456789012"))
        val order = testOrder(id = orderId)

        every { orderRepository.loadById(orderId) } returns order

        // when
        service.getOrderById(orderId)

        // then
        verify { orderRepository.loadById(orderId) }
        confirmVerified(orderRepository)
    }

    @Test
    fun `should return order with all properties intact`() {
        // given
        val orderId = OrderId(UUID.randomUUID())
        val originalOrder = testOrder(
            id = orderId,
            totalAmount = Price(250.75)
        )

        every { orderRepository.loadById(orderId) } returns originalOrder

        // when
        val result = service.getOrderById(orderId)

        // then
        assertThat(result).usingRecursiveComparison().isEqualTo(originalOrder)

        verify { orderRepository.loadById(orderId) }
        confirmVerified(orderRepository)
    }
}