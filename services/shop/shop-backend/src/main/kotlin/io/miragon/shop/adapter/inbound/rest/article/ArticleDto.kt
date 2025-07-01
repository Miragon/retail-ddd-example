package io.miragon.shop.adapter.inbound.rest.article

import io.miragon.shop.domain.article.Article
import java.util.*

data class ArticleDto(
    val id: UUID,
    val name: String,
    val description: String,
    val price: Double,
) {
    companion object {
        fun fromDomain(article: Article) = ArticleDto(
            id = article.id.value,
            name = article.name.value,
            description = article.description.value,
            price = article.price.value,
        )
    }
}