package io.miragon.shop.adapter.inbound.rest

import io.github.oshai.kotlinlogging.KotlinLogging
import io.miragon.shop.application.port.inbound.ArticleQuery
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/articles")
class LoadArticlesController(private val query: ArticleQuery) {

    private val log = KotlinLogging.logger {}

    @GetMapping
    fun loadArticles(): List<ArticleDto> {
        log.info { "Retrieving all articles" }
        val articles = query.loadAll()
        return articles.map { ArticleDto.fromDomain(it) }
    }
}