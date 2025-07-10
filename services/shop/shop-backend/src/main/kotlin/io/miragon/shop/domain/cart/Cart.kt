package io.miragon.shop.domain.cart

import io.github.oshai.kotlinlogging.KotlinLogging
import io.miragon.shop.domain.article.Article
import io.miragon.shop.domain.article.ArticleId
import io.miragon.shop.domain.shared.Price
import io.miragon.shop.domain.shared.Quantity
import io.miragon.shop.domain.shared.UserId

/**
 * A cart belongs to a specific user and contains a list of items.
 * It does not need a unique identifier, as one user can only have one cart at a time.
 */
data class Cart(
    val userId: UserId,
    val items: List<CartItem>
) {

    private val log = KotlinLogging.logger {}

    fun addItem(article: Article): Cart {
        val existingItem = items.find { it.articleId == article.id }
        if (existingItem != null) {
            val updatedQuantity = Quantity(existingItem.quantity.value + 1)
            val withoutItem = items.filter { it.articleId != article.id }
            val updatedItem = existingItem.copy(quantity = updatedQuantity, pricePerItem = article.price)
            return copy(items = withoutItem + updatedItem)
        } else {
            val newItem = CartItem.fromArticle(article, userId)
            return copy(items = items + newItem)
        }
    }

    fun removeItem(articleId: ArticleId): Cart {
        val existingItem = items.find { it.articleId == articleId }
        return if (existingItem == null) {
            log.info { "No item with id $articleId is on the cart. Remaining cart unchanged" }
            this
        } else {
            return copy(items = items - existingItem)
        }
    }

    fun totalPrice(): Price {
        return Price(items.sumOf { it.totalPrice().value })
    }

    fun isEmpty(): Boolean = items.isEmpty()

}