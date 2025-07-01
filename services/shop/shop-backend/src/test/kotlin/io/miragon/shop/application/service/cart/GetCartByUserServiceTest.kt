package io.miragon.shop.application.service.cart

import io.miragon.shop.application.port.outbound.CartRepository
import io.miragon.shop.domain.cart.testCart
import io.miragon.shop.domain.testUserId
import io.mockk.confirmVerified
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class GetCartByUserServiceTest {

    private val cartRepository = mockk<CartRepository>()
    private val service = GetCartByUserService(cartRepository)

    @Test
    fun `should return cart for user`() {

        // given
        val userId = testUserId()
        every { cartRepository.loadCart(userId) } returns testCart()

        // when
        val result = service.getCart(userId)

        // then
        assertThat(result).usingRecursiveComparison().isEqualTo(testCart())
        verify { cartRepository.loadCart(userId) }
        confirmVerified(cartRepository)
    }

}