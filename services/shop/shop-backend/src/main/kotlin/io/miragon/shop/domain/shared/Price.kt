package io.miragon.shop.domain.shared

class Price(value: Double) : ValueObject<Double>(value) {
    init {
        require(value >= 0) { "Article price must be greater than zero" }
    }
}