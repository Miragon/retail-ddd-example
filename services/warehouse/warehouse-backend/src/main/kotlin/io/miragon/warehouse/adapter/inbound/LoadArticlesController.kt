package io.miragon.warehouse.adapter.inbound

import io.miragon.warehouse.application.port.inbound.ArticleQuery
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/articles")
class LoadArticlesController(private val query: ArticleQuery) {

    @GetMapping
    fun loadArticles(): List<ArticleDto> {
        val articles = query.loadAll()
        return articles.map { ArticleDto.fromDomain(it) }
    }
}