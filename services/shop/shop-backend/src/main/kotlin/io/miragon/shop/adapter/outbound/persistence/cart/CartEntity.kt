package io.miragon.shop.adapter.outbound.persistence.cart

import jakarta.persistence.CascadeType
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.Id
import jakarta.persistence.OneToMany

@Entity(name = "cart")
class CartEntity(
    @Id
    @Column(name = "user_id", nullable = false)
    val userId: String,

    @OneToMany(mappedBy = "cart", cascade = [CascadeType.ALL], fetch = FetchType.LAZY, orphanRemoval = true)
    val items: MutableList<CartItemEntity> = mutableListOf()
) {
    companion object
}