package io.miragon.shop.adapter.outbound

import io.miragon.shop.adapter.outbound.ArticleMapper.toDomain
import io.miragon.shop.adapter.outbound.ArticleMapper.toEntity
import io.miragon.shop.application.port.outbound.ArticleRepository
import io.miragon.shop.domain.Article
import org.springframework.stereotype.Component

@Component
class ArticlePersistenceAdapter(
    private val articleJpaRepository: ArticleJpaRepository
) : ArticleRepository {

    override fun loadAll(): List<Article> {
        val entities = articleJpaRepository.findAll()
        return entities.map { it.toDomain() }
    }

    override fun save(article: Article) {
        articleJpaRepository.save(ArticleEntity.toEntity(article))
    }
} 