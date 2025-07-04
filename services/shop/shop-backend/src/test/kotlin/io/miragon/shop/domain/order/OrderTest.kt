package io.miragon.shop.domain.order

import io.miragon.shop.domain.article.ArticleId
import io.miragon.shop.domain.article.testArticle
import io.miragon.shop.domain.cart.testCart
import io.miragon.shop.domain.cart.testCartItem
import io.miragon.shop.domain.cart.testEmptyCart
import io.miragon.shop.domain.shared.Price
import io.miragon.shop.domain.shared.Quantity
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.jupiter.api.Test

class OrderTest {

    @Test
    fun `should create order from cart with multiple items`() {

        // given
        val article1 = testArticle(id = ArticleId(), price = 40.0)
        val article2 = testArticle(id = ArticleId(), price = 20.0)
        val articles = listOf(article1, article2)
        val cartItem1 = testCartItem(articleId = article1.id, quantity = Quantity(2))
        val cartItem2 = testCartItem(articleId = article2.id, quantity = Quantity(3))
        val cart = testCart(items = listOf(cartItem1, cartItem2))

        // when
        val order = Order.place(cart, articles)

        // then
        assertThat(order).usingRecursiveComparison().ignoringFields("id", "items.id", "orderDate").isEqualTo(
            testOrder(
                status = OrderStatus.PLACED,
                totalAmount = Price(140.0),
                items = listOf(
                    testOrderItem(articleId = article1.id, quantity = Quantity(2), pricePerItem = article1.price),
                    testOrderItem(articleId = article2.id, quantity = Quantity(3), pricePerItem = article2.price)
                ),
            )
        )
    }


    @Test
    fun `should throw exception when creating order from empty cart`() {
        // given
        val emptyCart = testEmptyCart()
        val articles = listOf(testArticle())

        // when and then
        assertThatThrownBy { Order.place(emptyCart, articles) }
            .isInstanceOf(IllegalArgumentException::class.java)
            .hasMessage("Cannot create order from empty cart")
    }

    @Test
    fun `should throw exception when article is missing for cart item`() {
        // given
        val article = testArticle()
        val cartItem = testCartItem(articleId = article.id)
        val cart = testCart(items = listOf(cartItem))
        val emptyArticles = emptyList<io.miragon.shop.domain.article.Article>()

        // when and then
        assertThatThrownBy { Order.place(cart, emptyArticles) }
            .isInstanceOf(IllegalArgumentException::class.java)
            .hasMessage("There is no article for cart-item ${article.id}")
    }

}