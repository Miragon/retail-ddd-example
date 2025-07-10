package io.miragon.shop.domain.article

import io.miragon.shop.domain.shared.ValueObject

class ArticleName(value: String) : ValueObject<String>(value) {
    init {
        require(value.isNotBlank()) { "Article name cannot be blank" }
    }
}