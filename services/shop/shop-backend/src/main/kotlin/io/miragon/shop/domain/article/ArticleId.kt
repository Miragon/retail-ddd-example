package io.miragon.shop.domain.article

import io.miragon.shop.domain.shared.ValueObject
import java.util.*

class ArticleId(value: UUID = UUID.randomUUID()) : ValueObject<UUID>(value) {
    constructor(uuid: String) : this(UUID.fromString(uuid))
}