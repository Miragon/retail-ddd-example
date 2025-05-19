package io.miragon.shop.domain.shared

data class ArticleDescription(val value: String) {
    init {
        require(value.isNotBlank()) { "Article description cannot be blank" }
    }
}