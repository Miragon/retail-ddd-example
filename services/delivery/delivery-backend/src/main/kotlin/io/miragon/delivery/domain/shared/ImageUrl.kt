package io.miragon.delivery.domain.shared

data class ImageUrl(val value: String) {
    init {
        require(value.isNotBlank()) { "Article image URL cannot be blank" }
    }
}