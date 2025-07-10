package io.miragon.shop.domain.order

import io.miragon.shop.domain.shared.Price
import io.miragon.shop.domain.shared.Quantity
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class OrderItemTest {

    @Test
    fun `should calculate total price correctly`() {
        val orderItem = testOrderItem(quantity = Quantity(3), pricePerItem = Price(25.5))
        val totalPrice = orderItem.totalPrice()
        assertThat(totalPrice).usingRecursiveComparison().isEqualTo(Price(76.5))
    }

}