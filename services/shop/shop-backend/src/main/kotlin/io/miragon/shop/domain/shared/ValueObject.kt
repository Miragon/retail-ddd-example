package io.miragon.shop.domain.shared

abstract class ValueObject<T>(val value: T) {

    override fun toString() = value.toString()

    override fun equals(other: Any?): Boolean {
        if (other == null || other::class != this::class) return false
        return this.value == (other as ValueObject<*>).value
    }

    override fun hashCode(): Int {
        return value?.hashCode() ?: 0
    }
}