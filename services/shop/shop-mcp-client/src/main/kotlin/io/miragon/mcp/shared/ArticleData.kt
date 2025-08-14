package io.miragon.mcp.shared

import java.util.*

data class ArticleData(
    val id: UUID,
    val name: String,
    val description: String,
    val price: Double
)