package io.miragon.shop.domain.shared

@JvmInline
value class ArticleName(val value: String) {
    init {
        require(value.isNotBlank()) { "Article name cannot be blank" }
    }
}