package io.miragon.shop.domain.cart

import io.miragon.shop.domain.article.testArticle
import io.miragon.shop.domain.shared.Price
import io.miragon.shop.domain.shared.Quantity
import io.miragon.shop.domain.testUserId
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class CartItemTest {

    @Test
    fun `should calculate total price correctly`() {
        val cartItem = testCartItem(quantity = Quantity(2), pricePerItem = Price(50.0))
        val totalPrice = cartItem.totalPrice()
        assertThat(totalPrice).isEqualTo(Price(100.0))
    }

    @Test
    fun `should create cart item from article`() {

        // given
        val userId = testUserId()
        val article = testArticle(price = 42.5)

        // when
        val cartItem = CartItem.fromArticle(article, userId)

        // then
        assertThat(cartItem.id).isNotNull()
        assertThat(cartItem).usingRecursiveComparison()
            .ignoringFields("id")
            .isEqualTo(
                testCartItem(
                    userId = userId,
                    articleId = article.id,
                    quantity = Quantity(1),
                    pricePerItem = Price(42.5)
                )
            )
    }

}