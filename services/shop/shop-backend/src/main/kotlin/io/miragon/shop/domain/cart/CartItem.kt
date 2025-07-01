package io.miragon.shop.domain.cart

import io.miragon.shop.domain.article.Article
import io.miragon.shop.domain.article.ArticleId
import io.miragon.shop.domain.shared.Price
import io.miragon.shop.domain.shared.Quantity
import io.miragon.shop.domain.shared.UserId

data class CartItem(
    val id: CartItemId,
    val userId: UserId,
    val articleId: ArticleId,
    val quantity: Quantity,
    val pricePerItem: Price
) {

    fun totalPrice(): Price = Price(pricePerItem.value * quantity.value)

    companion object {
        fun fromArticle(article: Article, userId: UserId) = CartItem(
            id = CartItemId(),
            userId = userId,
            articleId = article.id,
            quantity = Quantity(1),
            pricePerItem = article.price
        )
    }
}