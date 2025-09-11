package io.miragon.shop.adapter.inbound.rest.article

import io.github.oshai.kotlinlogging.KotlinLogging
import io.miragon.shop.application.port.inbound.ArticleQuery
import io.miragon.shop.domain.article.ArticleId
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.util.*

@RestController
@RequestMapping("/api/articles")
class GetArticleController(private val query: ArticleQuery) {

    private val log = KotlinLogging.logger {}

    @GetMapping("/{id}")
    fun getArticle(@PathVariable id: UUID): ArticleDto {
        log.info { "Retrieving article with id: $id" }
        val article = query.load(ArticleId(id))
        return ArticleDto.fromDomain(article)
    }
}