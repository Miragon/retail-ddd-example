package io.miragon.shop.application.port.inbound

import io.miragon.shop.domain.article.ArticleId
import io.miragon.shop.domain.cart.Cart
import io.miragon.shop.domain.shared.UserId

interface AddToCartUseCase {
    fun addToCart(userId: UserId, articleId: ArticleId): Cart
}