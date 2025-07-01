package io.miragon.shop.adapter.inbound.testdata

import io.miragon.shop.application.port.inbound.ImportArticleUseCase
import io.miragon.shop.domain.article.Article
import io.miragon.shop.domain.article.ArticleDescription
import io.miragon.shop.domain.article.ArticleId
import io.miragon.shop.domain.article.ArticleName
import io.miragon.shop.domain.shared.Price
import org.springframework.boot.context.event.ApplicationReadyEvent
import org.springframework.context.event.EventListener
import org.springframework.stereotype.Component
import java.util.*

@Component
class TestDataImporter(
    private val useCase: ImportArticleUseCase
) {

    @EventListener(ApplicationReadyEvent::class)
    fun importTestData() {
        val articles = testArticles()
        articles.forEach { useCase.importArticle(it) }
    }

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
        ),
        Article(
            id = ArticleId(UUID.fromString("a1b2c3d4-e5f6-4a5b-9c8d-7e6f5a4b3c2d")),
            name = ArticleName("Dell XPS 15 Laptop"),
            description = ArticleDescription("Premium 15-inch laptop with InfinityEdge display and high-performance components."),
            price = Price(1499.99),
        ),
        Article(
            id = ArticleId(UUID.fromString("b2c3d4e5-f6a7-5b6c-0d1e-8f9a0b1c2d3e")),
            name = ArticleName("Apple iPad Pro 12.9"),
            description = ArticleDescription("Powerful tablet with M2 chip, Liquid Retina XDR display, and Apple Pencil support."),
            price = Price(1099.00),
        ),
        Article(
            id = ArticleId(UUID.fromString("c3d4e5f6-a7b8-6c7d-1e2f-0a1b2c3d4e5f")),
            name = ArticleName("Anker PowerCore 26800"),
            description = ArticleDescription("High-capacity portable charger with multiple ports for charging multiple devices simultaneously."),
            price = Price(69.99),
        ),
        Article(
            id = ArticleId(UUID.fromString("d4e5f6a7-b8c9-7d8e-2f3a-1b2c3d4e5f6a")),
            name = ArticleName("LG 34WN80C-B UltraWide Monitor"),
            description = ArticleDescription("34-inch curved UltraWide QHD monitor with USB-C connectivity and HDR 10 support."),
            price = Price(699.99),
        ),
        Article(
            id = ArticleId(UUID.fromString("e5f6a7b8-c9d0-8e9f-3a4b-2c3d4e5f6a7b")),
            name = ArticleName("Bose QuietComfort 45 Headphones"),
            description = ArticleDescription("Premium noise cancelling headphones with high-fidelity audio and comfortable design for all-day wear."),
            price = Price(329.00),
        ),
        Article(
            id = ArticleId(UUID.fromString("f6a7b8c9-d0e1-9f0a-4b5c-3d4e5f6a7b8c")),
            name = ArticleName("Canon EOS R6 Camera"),
            description = ArticleDescription("Full-frame mirrorless camera with 20MP sensor, 4K video recording, and advanced autofocus system."),
            price = Price(2499.99),
        ),
        Article(
            id = ArticleId(UUID.fromString("a7b8c9d0-e1f2-0a1b-5c6d-4e5f6a7b8c9d")),
            name = ArticleName("Razer Blade 15 Gaming Laptop"),
            description = ArticleDescription("Powerful gaming laptop with RTX graphics, high refresh rate display, and customizable RGB keyboard."),
            price = Price(1799.99),
        ),
        Article(
            id = ArticleId(UUID.fromString("b8c9d0e1-f2a3-1b2c-6d7e-5f6a7b8c9d0e")),
            name = ArticleName("Samsung Galaxy S23 Ultra"),
            description = ArticleDescription("Flagship smartphone with 200MP camera, S Pen support, and powerful Snapdragon processor."),
            price = Price(1199.99),
        ),
        Article(
            id = ArticleId(UUID.fromString("c9d0e1f2-a3b4-2c3d-7e8f-6a7b8c9d0e1f")),
            name = ArticleName("Dyson V15 Detect Vacuum"),
            description = ArticleDescription("Cordless vacuum with laser dust detection, powerful suction, and advanced filtration system."),
            price = Price(749.99),
        ),
        Article(
            id = ArticleId(UUID.fromString("d0e1f2a3-b4c5-3d4e-8f9a-7b8c9d0e1f2a")),
            name = ArticleName("ASUS ROG Swift PG32UQX Monitor"),
            description = ArticleDescription("32-inch 4K HDR gaming monitor with Mini LED technology, 144Hz refresh rate, and G-SYNC Ultimate."),
            price = Price(2999.99),
        ),
        Article(
            id = ArticleId(UUID.fromString("e1f2a3b4-c5d6-4e5f-9a0b-8c9d0e1f2a3b")),
            name = ArticleName("Sonos Arc Soundbar"),
            description = ArticleDescription("Premium soundbar with Dolby Atmos, voice assistant support, and seamless multi-room audio integration."),
            price = Price(899.00),
        ),
        Article(
            id = ArticleId(UUID.fromString("f2a3b4c5-d6e7-5f6a-0b1c-9d0e1f2a3b4c")),
            name = ArticleName("DJI Mavic 3 Pro Drone"),
            description = ArticleDescription("Professional drone with Hasselblad camera, 4/3 CMOS sensor, and 46-minute flight time."),
            price = Price(2199.00),
        ),
        Article(
            id = ArticleId(UUID.fromString("a3b4c5d6-e7f8-6a7b-1c2d-0e1f2a3b4c5d")),
            name = ArticleName("Garmin Fenix 7 Sapphire"),
            description = ArticleDescription("Advanced multisport GPS watch with solar charging, touchscreen, and comprehensive health monitoring."),
            price = Price(899.99),
        ),
        Article(
            id = ArticleId(UUID.fromString("b4c5d6e7-f8a9-7b8c-2d3e-1f2a3b4c5d6e")),
            name = ArticleName("Microsoft Surface Studio 2+"),
            description = ArticleDescription("All-in-one creative workstation with 28-inch touchscreen display, adjustable hinge, and powerful hardware."),
            price = Price(4499.99),
        )
    )
} 
