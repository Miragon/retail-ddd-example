package io.miragon.shop.application.service.order

import io.github.oshai.kotlinlogging.KotlinLogging
import io.miragon.shop.application.port.inbound.PlaceOrderUseCase
import io.miragon.shop.application.port.outbound.ArticleRepository
import io.miragon.shop.application.port.outbound.CartRepository
import io.miragon.shop.application.port.outbound.OrderRepository
import io.miragon.shop.domain.order.Order
import io.miragon.shop.domain.shared.UserId
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class PlaceOrderService(
    private val orderRepository: OrderRepository,
    private val articleRepository: ArticleRepository,
    private val cartRepository: CartRepository,
) : PlaceOrderUseCase {

    private val log = KotlinLogging.logger {}

    override fun placeOrder(userId: UserId): Order {

        val cart = cartRepository.loadCart(userId)
        if (cart.isEmpty()) throw IllegalArgumentException("Cannot place order with empty cart")

        val articlesInCart = cart.items.map { it.articleId }
        val articles = articleRepository.loadByIds(articlesInCart)

        val order = Order.place(cart = cart, articles = articles)
        orderRepository.save(order)
        cartRepository.deleteCart(userId)

        log.info { "Completed order ${order.id} for user $userId with ${order.items.size} items. Cart cleared." }

        return order
    }
}