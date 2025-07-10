package io.miragon.shop.domain.order

import io.miragon.shop.domain.shared.ValueObject
import java.util.*

class OrderId(value: UUID = UUID.randomUUID()) : ValueObject<UUID>(value)