package io.miragon.delivery.adapter.outbound

import io.miragon.delivery.application.port.outbound.ArticleRepository
import io.miragon.delivery.domain.shared.ArticleName
import org.springframework.stereotype.Component
import java.util.*

@Component
class ArticleInMemoryRepository : ArticleRepository {

    private val articles = testArticles()

    override fun loadAll() = articles

    private fun testArticles() = listOf(
        _root_ide_package_.io.miragon.delivery.domain.Article(
            id = _root_ide_package_.io.miragon.delivery.domain.shared.ArticleId(UUID.fromString("246b5980-ff9a-4cf5-803b-cd2d50fe60da")),
            name = ArticleName("Logitech MX Master 3S"),
            description = _root_ide_package_.io.miragon.delivery.domain.shared.ArticleDescription("Advanced wireless mouse with ultra-fast scrolling and ergonomic design."),
            price = _root_ide_package_.io.miragon.delivery.domain.shared.Price(99.99),
        ),
        _root_ide_package_.io.miragon.delivery.domain.Article(
            id = _root_ide_package_.io.miragon.delivery.domain.shared.ArticleId(UUID.fromString("f2b5c8a0-1d3e-4c5b-9f3e-7d6f8a2b1c3d")),
            name = ArticleName("Samsung 980 PRO 1TB SSD"),
            description = _root_ide_package_.io.miragon.delivery.domain.shared.ArticleDescription("High-performance NVMe SSD with PCIe 4.0 for gaming and heavy workloads."),
            price = _root_ide_package_.io.miragon.delivery.domain.shared.Price(129.99),
        ),
        _root_ide_package_.io.miragon.delivery.domain.Article(
            id = _root_ide_package_.io.miragon.delivery.domain.shared.ArticleId(UUID.fromString("d7e9a1e0-1234-4c5b-9876-abcdef123456")),
            name = ArticleName("Keychron K2 Mechanical Keyboard"),
            description = _root_ide_package_.io.miragon.delivery.domain.shared.ArticleDescription("Compact wireless mechanical keyboard with RGB lighting and hot-swappable keys."),
            price = _root_ide_package_.io.miragon.delivery.domain.shared.Price(89.00),
        ),
        _root_ide_package_.io.miragon.delivery.domain.Article(
            id = _root_ide_package_.io.miragon.delivery.domain.shared.ArticleId(UUID.fromString("0f5e45d3-aaa3-4cde-a1b2-9e8f0d1a2b3c")),
            name = ArticleName("Sony WH-1000XM5 Headphones"),
            description = _root_ide_package_.io.miragon.delivery.domain.shared.ArticleDescription("Noise-canceling over-ear headphones with up to 30 hours of battery life."),
            price = _root_ide_package_.io.miragon.delivery.domain.shared.Price(349.99),
        )
    )
}