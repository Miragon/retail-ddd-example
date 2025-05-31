package io.miragon.shop.adapter.outbound

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import java.util.*

@Entity(name = "articles")
class ArticleEntity(
    @Id
    @Column(name = "id", nullable = false)
    val id: UUID,

    @Column(name = "name", nullable = false)
    val name: String,

    @Column(name = "description", nullable = false)
    val description: String,

    @Column(name = "price", nullable = false)
    val price: Double
) {
    companion object
}