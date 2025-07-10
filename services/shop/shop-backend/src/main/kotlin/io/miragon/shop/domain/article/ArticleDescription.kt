package io.miragon.shop.domain.article

import io.miragon.shop.domain.shared.ValueObject

class ArticleDescription(value: String) : ValueObject<String>(value) {
    init {
        require(value.isNotBlank()) { "Article description cannot be blank" }
    }
}