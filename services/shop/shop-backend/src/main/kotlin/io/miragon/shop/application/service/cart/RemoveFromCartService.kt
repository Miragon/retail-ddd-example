package io.miragon.shop.application.service.cart

import io.github.oshai.kotlinlogging.KotlinLogging
import io.miragon.shop.application.port.inbound.RemoveFromCartUseCase
import io.miragon.shop.application.port.outbound.CartRepository
import io.miragon.shop.domain.article.ArticleId
import io.miragon.shop.domain.cart.Cart
import io.miragon.shop.domain.shared.UserId
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class RemoveFromCartService(
    private val cartRepository: CartRepository,
) : RemoveFromCartUseCase {

    private val log = KotlinLogging.logger {}

    override fun removeFromCart(userId: UserId, articleId: ArticleId): Cart {
        val cart = cartRepository.loadCart(userId)
        val updatedCart = cart.removeItem(articleId)
        cartRepository.storeCart(updatedCart)
        log.info { "Removed article $articleId from cart of user $userId: $updatedCart" }
        return updatedCart
    }
}