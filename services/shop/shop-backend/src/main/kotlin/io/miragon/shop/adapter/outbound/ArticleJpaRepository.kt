package io.miragon.shop.adapter.outbound

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface ArticleJpaRepository : JpaRepository<ArticleEntity, UUID> 