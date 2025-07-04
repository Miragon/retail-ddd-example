package io.miragon.shop.application.service.order

import io.miragon.shop.application.port.outbound.OrderRepository
import io.miragon.shop.domain.order.testOrder
import io.miragon.shop.domain.testUserId
import io.mockk.confirmVerified
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class GetOrdersServiceTest {

    private val orderRepository = mockk<OrderRepository>()
    private val underTest = GetOrdersService(orderRepository)

    @Test
    fun `should return list of orders for user`() {

        // given
        val userId = testUserId()
        val order = testOrder()
        every { orderRepository.loadById(userId) } returns listOf(order)

        // when
        val result = underTest.getOrders(userId)

        // then
        assertThat(result).usingRecursiveComparison().isEqualTo(listOf(testOrder()))
        verify { orderRepository.loadById(userId) }
        confirmVerified(orderRepository)
    }
}
