package io.miragon.shop.adapter.outbound.persistence.article

import io.miragon.shop.adapter.outbound.persistence.article.ArticleMapper.toDomain
import io.miragon.shop.adapter.outbound.persistence.article.ArticleMapper.toEntity
import io.miragon.shop.application.port.outbound.ArticleRepository
import io.miragon.shop.domain.article.Article
import io.miragon.shop.domain.article.ArticleId
import org.springframework.stereotype.Component

@Component
class ArticlePersistenceAdapter(
    private val articleJpaRepository: ArticleJpaRepository
) : ArticleRepository {

    override fun loadAll(): List<Article> {
        val entities = articleJpaRepository.findAll()
        return entities.map { it.toDomain() }
    }

    override fun loadByIds(articleIds: List<ArticleId>): List<Article> {
        val ids = articleIds.map { it.value }
        if (ids.isEmpty()) return emptyList()
        val entities = articleJpaRepository.findAllByIdIn(ids)
        return entities.map { it.toDomain() }
    }

    override fun save(article: Article) {
        articleJpaRepository.save(ArticleEntity.Companion.toEntity(article))
    }

    override fun findById(articleId: ArticleId): Article? {
        return articleJpaRepository.findById(articleId.value)
            .map { it.toDomain() }
            .orElse(null)
    }
} 