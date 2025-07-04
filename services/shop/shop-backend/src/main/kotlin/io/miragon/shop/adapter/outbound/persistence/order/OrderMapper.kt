package io.miragon.shop.adapter.outbound.persistence.order

import io.miragon.shop.domain.article.ArticleId
import io.miragon.shop.domain.article.ArticleName
import io.miragon.shop.domain.order.Order
import io.miragon.shop.domain.order.OrderId
import io.miragon.shop.domain.order.OrderItem
import io.miragon.shop.domain.order.OrderItemId
import io.miragon.shop.domain.shared.Price
import io.miragon.shop.domain.shared.Quantity
import io.miragon.shop.domain.shared.UserId

object OrderMapper {

    fun OrderEntity.toDomain(): Order = Order(
        id = OrderId(id),
        userId = UserId(userId),
        items = items.map { it.toDomain() },
        status = status,
        orderDate = orderDate,
        totalAmount = Price(totalAmount)
    )

    fun OrderItemEntity.toDomain(): OrderItem = OrderItem(
        id = OrderItemId(id),
        articleId = ArticleId(articleId),
        articleName = ArticleName(articleName),
        quantity = Quantity(quantity),
        pricePerItem = Price(pricePerItem)
    )

    fun OrderEntity.Companion.toEntity(
        order: Order
    ) = OrderEntity(
        id = order.id.value,
        userId = order.userId.value,
        status = order.status,
        orderDate = order.orderDate,
        totalAmount = order.totalAmount.value,
        items = order.items.map {
            OrderItemEntity(
                id = it.id.value,
                orderId = order.id.value,
                articleId = it.articleId.value,
                articleName = it.articleName.value,
                quantity = it.quantity.value,
                pricePerItem = it.pricePerItem.value,
            )
        }
    )

}