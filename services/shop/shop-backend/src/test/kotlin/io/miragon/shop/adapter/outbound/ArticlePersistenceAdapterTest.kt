package io.miragon.shop.adapter.outbound

import io.miragon.shop.adapter.outbound.persistence.article.ArticlePersistenceAdapter
import io.miragon.shop.domain.article.ArticleId
import io.miragon.shop.domain.article.testArticle
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest
import org.springframework.boot.jpa.test.autoconfigure.TestEntityManager
import org.springframework.context.annotation.Import
import org.springframework.test.context.jdbc.Sql

@DataJpaTest
@Import(ArticlePersistenceAdapter::class)
class ArticlePersistenceAdapterTest {

    @Autowired
    private lateinit var adapter: ArticlePersistenceAdapter

    @Autowired
    private lateinit var entityManager: TestEntityManager

    @Test
    @Sql("/articles.sql")
    fun `should load all articles from database`() {
        // when
        val articles = adapter.loadAll()

        // then
        assertThat(articles).hasSize(2)
        assertThat(articles).usingRecursiveComparison().isEqualTo(
            listOf(
                testArticle(
                    id = ArticleId("246b5980-ff9a-4cf5-803b-cd2d50fe60da"),
                    name = "Logitech MX Master 3S",
                    description = "Advanced wireless mouse with ultra-fast scrolling and ergonomic design.",
                    price = 99.99
                ),
                testArticle(
                    id = ArticleId("f2b5c8a0-1d3e-4c5b-9f3e-7d6f8a2b1c3d"),
                    name = "Samsung 980 PRO 1TB SSD",
                    description = "High-performance NVMe SSD with PCIe 4.0 for gaming and heavy workloads.",
                    price = 129.99
                )
            )
        )
    }

    @Test
    fun `should save article to database`() {
        // given
        val article = testArticle(
            id = ArticleId(),
            name = "Test Article",
            description = "Test Description",
            price = 199.99
        )

        // when
        adapter.save(article)
        entityManager.flush()

        // then
        val loadedArticles = adapter.loadAll()
        assertThat(loadedArticles).hasSize(1)
        assertThat(loadedArticles[0]).usingRecursiveComparison().isEqualTo(
            testArticle(
                id = article.id,
                name = article.name.value,
                description = article.description.value,
                price = article.price.value
            )
        )
    }
} 