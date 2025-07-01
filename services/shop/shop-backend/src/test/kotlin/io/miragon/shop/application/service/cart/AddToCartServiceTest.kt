package io.miragon.shop.application.service.cart

import io.miragon.shop.application.port.outbound.ArticleRepository
import io.miragon.shop.application.port.outbound.CartRepository
import io.miragon.shop.domain.article.testArticle
import io.miragon.shop.domain.cart.testCart
import io.miragon.shop.domain.cart.testCartItem
import io.miragon.shop.domain.cart.testEmptyCart
import io.miragon.shop.domain.shared.Quantity
import io.miragon.shop.domain.testUserId
import io.mockk.confirmVerified
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.jupiter.api.Test

class AddToCartServiceTest {

    private val cartRepository = mockk<CartRepository>(relaxed = true)
    private val articleRepository = mockk<ArticleRepository>(relaxed = true)
    private val service = AddToCartService(cartRepository, articleRepository)

    @Test
    fun `should add article to cart when article exists`() {

        // given
        val userId = testUserId()
        val article = testArticle()
        every { articleRepository.findById(article.id) } returns article
        every { cartRepository.loadCart(userId) } returns testEmptyCart()

        // when
        val result = service.addToCart(userId, article.id)

        // then
        val expectedItem = testCartItem(quantity = Quantity(1))
        val expectedCart = testCart(userId = userId, items = listOf(expectedItem))
        assertThat(result).usingRecursiveComparison().ignoringFields("items.id").isEqualTo(expectedCart)

        verify { articleRepository.findById(article.id) }
        verify { cartRepository.loadCart(userId) }
        verify { cartRepository.storeCart(any()) }
        confirmVerified(articleRepository, cartRepository)
    }

    @Test
    fun `should increase quantity when adding existing article`() {

        // given
        val userId = testUserId()
        val article = testArticle()
        every { articleRepository.findById(article.id) } returns article
        every { cartRepository.loadCart(userId) } returns testCart()

        // when
        val result = service.addToCart(userId, article.id)

        // then
        val expectedCart = testCart(items = listOf(testCartItem(quantity = Quantity(3))))
        assertThat(result).usingRecursiveComparison().isEqualTo(expectedCart)
        verify { articleRepository.findById(article.id) }
        verify { cartRepository.loadCart(userId) }
        verify { cartRepository.storeCart(any()) }
        confirmVerified(articleRepository, cartRepository)
    }

    @Test
    fun `should throw exception when article not found`() {

        // given
        val userId = testUserId()
        val article = testArticle()
        every { articleRepository.findById(article.id) } returns null

        // when and then
        assertThatThrownBy { service.addToCart(userId, article.id) }
            .isInstanceOf(IllegalArgumentException::class.java)
            .hasMessage("Article not found: ${article.id}")

        verify { articleRepository.findById(article.id) }
        verify(exactly = 0) { cartRepository.loadCart(any()) }
        verify(exactly = 0) { cartRepository.storeCart(any()) }
        confirmVerified(articleRepository, cartRepository)
    }
}