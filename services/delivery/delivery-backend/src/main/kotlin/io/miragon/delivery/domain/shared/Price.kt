package io.miragon.delivery.domain.shared

data class Price(val value: Double) {
    init {
        require(value > 0) { "Article price must be greater than zero" }
    }
}