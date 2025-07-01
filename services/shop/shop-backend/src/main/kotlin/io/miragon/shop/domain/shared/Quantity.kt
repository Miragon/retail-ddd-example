package io.miragon.shop.domain.shared

class Quantity(value: Int) : ValueObject<Int>(value) {
    init {
        require(value > 0) { "Quantity must be greater than zero" }
    }
}