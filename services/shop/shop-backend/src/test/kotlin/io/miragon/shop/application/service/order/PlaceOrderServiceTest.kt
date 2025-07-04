package io.miragon.shop.application.service.order

import io.miragon.shop.application.port.outbound.ArticleRepository
import io.miragon.shop.application.port.outbound.CartRepository
import io.miragon.shop.application.port.outbound.OrderRepository
import io.miragon.shop.domain.article.ArticleId
import io.miragon.shop.domain.article.testArticle
import io.miragon.shop.domain.cart.testCart
import io.miragon.shop.domain.cart.testCartItem
import io.miragon.shop.domain.order.Order
import io.miragon.shop.domain.order.testOrder
import io.miragon.shop.domain.order.testOrderItem
import io.miragon.shop.domain.shared.Price
import io.miragon.shop.domain.shared.Quantity
import io.miragon.shop.domain.testUserId
import io.mockk.confirmVerified
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class PlaceOrderServiceTest {

    private val orderRepository = mockk<OrderRepository>(relaxed = true)
    private val articleRepository = mockk<ArticleRepository>()
    private val cartRepository = mockk<CartRepository>(relaxed = true)
    private val service = PlaceOrderService(orderRepository, articleRepository, cartRepository)

    @Test
    fun `should place order`() {

        // given
        val userId = testUserId()
        val articleOne = testArticle(id = ArticleId(), price = 200.0)
        val articleTwo = testArticle(id = ArticleId(), price = 100.0)
        val articleList = listOf(articleOne, articleTwo)
        every { articleRepository.loadByIds(listOf(articleOne.id, articleTwo.id)) } returns articleList
        every { cartRepository.loadCart(userId) } returns testCart(
            items = listOf(
                testCartItem(articleId = articleOne.id, quantity = Quantity(20)),
                testCartItem(articleId = articleTwo.id, quantity = Quantity(10))
            )
        )

        // when
        val result = service.placeOrder(userId)

        // then
        val itemOne = testOrderItem(quantity = Quantity(20), articleId = articleOne.id, pricePerItem = articleOne.price)
        val itemTwo = testOrderItem(quantity = Quantity(10), articleId = articleTwo.id, pricePerItem = articleTwo.price)
        assertThat(result).usingRecursiveComparison().ignoringFields("id", "items.id", "orderDate").isEqualTo(
            testOrder(items = listOf(itemOne, itemTwo), totalAmount = Price(5000.0))
        )

        verify { cartRepository.loadCart(userId) }
        verify { articleRepository.loadByIds(listOf(articleOne.id, articleTwo.id)) }
        verify { orderRepository.save(any<Order>()) }
        verify { cartRepository.deleteCart(userId) }
        confirmVerified(orderRepository, articleRepository, cartRepository)
    }

}