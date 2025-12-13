package io.miragon.shop.adapter.inbound.rest

import com.fasterxml.jackson.databind.ObjectMapper
import com.ninjasquad.springmockk.MockkBean
import io.miragon.shop.adapter.inbound.rest.article.ArticleDto
import io.miragon.shop.adapter.inbound.rest.article.LoadArticlesController
import io.miragon.shop.application.port.inbound.ArticleQuery
import io.miragon.shop.domain.article.Article
import io.miragon.shop.domain.article.ArticleId
import io.miragon.shop.domain.article.testArticle
import io.mockk.every
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.content
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import java.util.*

@WebMvcTest(LoadArticlesController::class)
@AutoConfigureMockMvc(addFilters = false)
class LoadArticlesControllerTest {

    @Autowired
    private lateinit var mockMvc: MockMvc

    @MockkBean
    private lateinit var articleQuery: ArticleQuery

    @Test
    fun `loadArticles returns list of articles`() {
        val articles = createTestArticles()
        val expectedResponse = createExpectedArticleDtos()
        every { articleQuery.loadAll() } returns articles
        mockMvc.perform(get("/api/articles"))
            .andExpect(status().isOk)
            .andExpect(content().json(ObjectMapper().writeValueAsString(expectedResponse)))
    }

    private fun createTestArticles(): List<Article> = listOf(
        testArticle(
            id = ArticleId("4ca4a9fe-6ce6-41a1-87f6-f99d4c6882a4"),
            name = "Test Article 1",
            description = "Test Description 1",
            price = 99.99
        ),
        testArticle(
            id = ArticleId("5ca4a9fe-6ce6-41a1-87f6-f99d4c6882a5"),
            name = "Test Article 2",
            description = "Test Description 2",
            price = 149.99
        )
    )

    private fun createExpectedArticleDtos(): List<ArticleDto> = listOf(
        testArticleDto(
            id = UUID.fromString("4ca4a9fe-6ce6-41a1-87f6-f99d4c6882a4"),
            name = "Test Article 1",
            description = "Test Description 1",
            price = 99.99
        ),
        testArticleDto(
            id = UUID.fromString("5ca4a9fe-6ce6-41a1-87f6-f99d4c6882a5"),
            name = "Test Article 2",
            description = "Test Description 2",
            price = 149.99
        )
    )
} 