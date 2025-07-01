package io.miragon.shop.adapter.outbound.persistence.cart

import org.springframework.data.jpa.repository.JpaRepository

interface CartJpaRepository : JpaRepository<CartEntity, String> {
    fun findByUserId(userId: String): CartEntity?
}