package io.miragon.delivery.adapter.inbound

import java.util.*

data class ArticleDto(
    val id: UUID,
    val name: String,
    val description: String,
    val price: Double,
) {
    companion object {
        fun fromDomain(article: io.miragon.delivery.domain.Article) = ArticleDto(
            id = article.id.value,
            name = article.name.value,
            description = article.description.value,
            price = article.price.value,
        )
    }
}