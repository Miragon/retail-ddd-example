package io.miragon.shop.domain.article

import io.miragon.shop.domain.shared.ValueObject

class ImageUrl(value: String) : ValueObject<String>(value) {
    init {
        require(value.isNotBlank()) { "Article image URL cannot be blank" }
    }
}