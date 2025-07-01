package io.miragon.shop.adapter.outbound.persistence.cart

import io.miragon.shop.adapter.outbound.persistence.cart.CartMapper.toDomain
import io.miragon.shop.adapter.outbound.persistence.cart.CartMapper.toEntity
import io.miragon.shop.application.port.outbound.CartRepository
import io.miragon.shop.domain.cart.Cart
import io.miragon.shop.domain.shared.UserId
import org.springframework.stereotype.Component

@Component
class CartPersistenceAdapter(
    private val cartJpaRepository: CartJpaRepository
) : CartRepository {

    override fun loadCart(userId: UserId): Cart {
        val cartEntity = cartJpaRepository.findByUserId(userId.value)
        return cartEntity?.toDomain() ?: Cart(userId = userId, items = emptyList())
    }

    override fun storeCart(cart: Cart) {
        val cartEntity = CartEntity.toEntity(cart)
        cartJpaRepository.save(cartEntity)
    }

    override fun deleteCart(userId: UserId) {
        cartJpaRepository.deleteById(userId.value)
    }
}