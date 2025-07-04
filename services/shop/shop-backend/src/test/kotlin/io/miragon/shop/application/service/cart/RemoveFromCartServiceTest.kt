package io.miragon.shop.application.service.cart

import io.miragon.shop.application.port.outbound.CartRepository
import io.miragon.shop.domain.article.ArticleId
import io.miragon.shop.domain.cart.testCart
import io.miragon.shop.domain.cart.testCartItem
import io.miragon.shop.domain.shared.Quantity
import io.miragon.shop.domain.testUserId
import io.mockk.confirmVerified
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class RemoveFromCartServiceTest {

    private val cartRepository = mockk<CartRepository>(relaxed = true)
    private val service = RemoveFromCartService(cartRepository)

    @Test
    fun `should remove item from cart`() {

        // given
        val userId = testUserId()

        val firstArticleId = ArticleId()
        val secondArticleId = ArticleId()
        val firstItem = testCartItem(articleId = firstArticleId, quantity = Quantity(20))
        val secondItem = testCartItem(articleId = secondArticleId, quantity = Quantity(10))
        every { cartRepository.loadCart(userId) } returns testCart(items = listOf(firstItem, secondItem))

        // when
        val result = service.removeFromCart(userId, firstArticleId)

        // then
        assertThat(result).usingRecursiveComparison().isEqualTo(testCart(items = listOf(secondItem)))
        verify { cartRepository.loadCart(userId) }
        verify { cartRepository.storeCart(any()) }
        confirmVerified(cartRepository)
    }
}