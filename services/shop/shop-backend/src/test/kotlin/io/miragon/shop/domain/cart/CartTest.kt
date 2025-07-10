package io.miragon.shop.domain.cart

import io.miragon.shop.domain.article.ArticleId
import io.miragon.shop.domain.article.testArticle
import io.miragon.shop.domain.shared.Price
import io.miragon.shop.domain.shared.Quantity
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class CartTest {

    @Test
    fun `should add new item to cart`() {

        // given
        val existingItem = testCartItem()
        val cart = testCart(items = listOf(existingItem))
        val newArticle = testArticle(id = ArticleId(), name = "Different Article")

        // when
        val updatedCart = cart.addItem(newArticle)

        // then
        assertThat(updatedCart.items).hasSize(2)
        assertThat(updatedCart.items[0]).isEqualTo(existingItem)
        assertThat(updatedCart.items[1]).usingRecursiveComparison().ignoringFields("id").isEqualTo(
            testCartItem(
                articleId = newArticle.id,
                quantity = Quantity(1),
                pricePerItem = newArticle.price
            )
        )
    }

    @Test
    fun `should increase quantity when adding existing item`() {

        // given
        val article = testArticle()
        val existingItem = testCartItem(articleId = article.id, quantity = Quantity(5))
        val cart = testCart(items = listOf(existingItem))

        // when
        val updatedCart = cart.addItem(article)

        // then
        val expectedItem = testCartItem(articleId = article.id, quantity = Quantity(6))
        val expectedCart = testCart(items = listOf(expectedItem))
        assertThat(updatedCart).usingRecursiveComparison().isEqualTo(expectedCart)
    }

    @Test
    fun `should remove item from cart`() {

        // given
        val item1 = testCartItem()
        val item2 = testCartItem(articleId = ArticleId())
        val cart = testCart(items = listOf(item1, item2))

        // when
        val updatedCart = cart.removeItem(item1.articleId)

        // then
        val expectedCart = cart.removeItem(item1.articleId)
        assertThat(updatedCart).usingRecursiveComparison().isEqualTo(expectedCart)
    }

    @Test
    fun `should calculate total price correctly`() {
        val item1 = testCartItem(quantity = Quantity(2), pricePerItem = Price(50.0))
        val item2 = testCartItem(quantity = Quantity(1), pricePerItem = Price(75.0))
        val cart = testCart(items = listOf(item1, item2))
        val totalPrice = cart.totalPrice()
        val expectedPrice = Price(175.0)
        assertThat(totalPrice).usingRecursiveComparison().isEqualTo(expectedPrice)
    }

    @Test
    fun `should return zero total price for empty cart`() {
        val cart = testEmptyCart()
        val totalPrice = cart.totalPrice()
        val expectedPrice = Price(0.0)
        assertThat(totalPrice).usingRecursiveComparison().isEqualTo(expectedPrice)
    }

    @Test
    fun `should return true when cart is empty`() {
        val cart = testEmptyCart()
        val isEmpty = cart.isEmpty()
        assertThat(isEmpty).isTrue()
    }

    @Test
    fun `should return false when cart has items`() {
        val cart = testCart()
        val isEmpty = cart.isEmpty()
        assertThat(isEmpty).isFalse()
    }
}