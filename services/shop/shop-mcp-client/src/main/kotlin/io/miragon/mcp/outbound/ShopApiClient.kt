package io.miragon.mcp.outbound

import io.miragon.mcp.shared.ArticleData
import org.springframework.core.ParameterizedTypeReference
import org.springframework.http.HttpMethod
import org.springframework.web.client.RestTemplate
import java.util.*

class ShopApiClient(
    private val restTemplate: RestTemplate,
    private val shopBackendProperties: ShopBackendProperties
) {

    fun loadAll(): List<ArticleData> {
        val url = "${shopBackendProperties.url}/api/articles"
        val responseType = object : ParameterizedTypeReference<List<ArticleResponseDto>>() {}
        val response = restTemplate.exchange(url, HttpMethod.GET, null, responseType)
        return response.body?.map { it.toArticleData() } ?: emptyList()
    }

    fun loadById(id: String): ArticleData {
        val url = "${shopBackendProperties.url}/api/articles/$id"
        val response = restTemplate.getForObject(url, ArticleResponseDto::class.java)
        if (response == null) throw RuntimeException("Article with id $id not found")
        return response.toArticleData()
    }

    private data class ArticleResponseDto(
        val id: UUID,
        val name: String,
        val description: String,
        val price: Double
    ) {
        fun toArticleData() = ArticleData(
            id = id,
            name = name,
            description = description,
            price = price
        )
    }
}