package io.miragon.shop.application.service.cart

import io.github.oshai.kotlinlogging.KotlinLogging
import io.miragon.shop.application.port.inbound.AddToCartUseCase
import io.miragon.shop.application.port.outbound.ArticleRepository
import io.miragon.shop.application.port.outbound.CartRepository
import io.miragon.shop.domain.article.ArticleId
import io.miragon.shop.domain.cart.Cart
import io.miragon.shop.domain.shared.UserId
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class AddToCartService(
    private val cartRepository: CartRepository,
    private val articleRepository: ArticleRepository
) : AddToCartUseCase {

    private val log = KotlinLogging.logger {}

    override fun addToCart(
        userId: UserId,
        articleId: ArticleId,
    ): Cart {

        val article = articleRepository.findById(articleId)
        if (article == null) throw IllegalArgumentException("Article not found: $articleId")

        val cart = cartRepository.loadCart(userId)
        val updatedCart = cart.addItem(article)

        cartRepository.storeCart(updatedCart)
        log.info { "Added article $articleId to cart of user $userId: $updatedCart" }

        return updatedCart
    }
}