package io.miragon.warehouse.adapter.outbound

import io.miragon.warehouse.application.port.outbound.ArticleRepository
import io.miragon.warehouse.domain.Article
import io.miragon.warehouse.domain.shared.ArticleDescription
import io.miragon.warehouse.domain.shared.ArticleId
import io.miragon.warehouse.domain.shared.ArticleName
import io.miragon.warehouse.domain.shared.Price
import org.springframework.stereotype.Component
import java.util.*

@Component
class ArticleInMemoryRepository : ArticleRepository {

    private val articles = testArticles()

    override fun loadAll() = articles

    private fun testArticles() = listOf(
        Article(
            id = ArticleId(UUID.fromString("246b5980-ff9a-4cf5-803b-cd2d50fe60da")),
            name = ArticleName("Logitech MX Master 3S"),
            description = ArticleDescription("Advanced wireless mouse with ultra-fast scrolling and ergonomic design."),
            price = Price(99.99),
        ),
        Article(
            id = ArticleId(UUID.fromString("f2b5c8a0-1d3e-4c5b-9f3e-7d6f8a2b1c3d")),
            name = ArticleName("Samsung 980 PRO 1TB SSD"),
            description = ArticleDescription("High-performance NVMe SSD with PCIe 4.0 for gaming and heavy workloads."),
            price = Price(129.99),
        ),
        Article(
            id = ArticleId(UUID.fromString("d7e9a1e0-1234-4c5b-9876-abcdef123456")),
            name = ArticleName("Keychron K2 Mechanical Keyboard"),
            description = ArticleDescription("Compact wireless mechanical keyboard with RGB lighting and hot-swappable keys."),
            price = Price(89.00),
        ),
        Article(
            id = ArticleId(UUID.fromString("0f5e45d3-aaa3-4cde-a1b2-9e8f0d1a2b3c")),
            name = ArticleName("Sony WH-1000XM5 Headphones"),
            description = ArticleDescription("Noise-canceling over-ear headphones with up to 30 hours of battery life."),
            price = Price(349.99),
        )
    )
}