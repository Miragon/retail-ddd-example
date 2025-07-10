package io.miragon.shop.domain.order

import io.miragon.shop.domain.article.Article
import io.miragon.shop.domain.cart.Cart
import io.miragon.shop.domain.cart.CartItem
import io.miragon.shop.domain.shared.Price
import io.miragon.shop.domain.shared.UserId
import java.time.LocalDateTime

data class Order(
    val id: OrderId = OrderId(),
    val userId: UserId,
    val items: List<OrderItem>,
    val status: OrderStatus,
    val orderDate: LocalDateTime,
    val totalAmount: Price
) {
    companion object {
        fun place(cart: Cart, articles: List<Article>): Order {
            require(!cart.isEmpty()) { "Cannot create order from empty cart" }
            val orderItems = cart.items.map { createOrderItem(it, articles) }
            val totalAmount = orderItems.sumOf { it.pricePerItem.value * it.quantity.value }
            return Order(
                userId = cart.userId,
                items = orderItems,
                status = OrderStatus.PLACED,
                orderDate = LocalDateTime.now(),
                totalAmount = Price(totalAmount)
            )
        }

        private fun createOrderItem(
            cartItem: CartItem,
            articles: List<Article>
        ): OrderItem {
            val article = articles.find { it.id == cartItem.articleId }
            requireNotNull(article) { "There is no article for cart-item ${cartItem.articleId}" }
            return OrderItem(
                id = OrderItemId(),
                articleId = cartItem.articleId,
                articleName = article.name,
                quantity = cartItem.quantity,
                pricePerItem = article.price,
            )
        }
    }
}