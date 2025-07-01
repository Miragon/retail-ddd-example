package io.miragon.shop.application.service.cart

import io.miragon.shop.application.port.inbound.CartQuery
import io.miragon.shop.application.port.outbound.CartRepository
import io.miragon.shop.domain.shared.UserId
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
class GetCartByUserService(
    private val cartRepository: CartRepository
) : CartQuery {
    override fun getCart(userId: UserId) = cartRepository.loadCart(userId)
}