package io.miragon.shop.domain.cart

import io.miragon.shop.domain.shared.ValueObject
import java.util.*

class CartItemId(value: UUID = UUID.randomUUID()) : ValueObject<UUID>(value)