package io.holunda.academy.axon.dailymeal.view

import io.holunda.academy.axon.dailymeal.api.*
import mu.KLogging
import org.axonframework.eventhandling.EventHandler
import org.springframework.stereotype.Component

@Component
class InMemoryOrderProjection() {

  companion object : KLogging()

  private val orders: MutableSet<OrderDto> = mutableSetOf()

  @EventHandler
  fun on(e: OrderOpenedEvent) {
    if (findOrder(e.orderId) != null) {
      logger.error { "Duplicate order found, $e" }
    } else {
      orders.add(
        OrderDto(
          orderId = e.orderId,
          orderStatus = OrderStatus.OPEN
        )
      )
    }
  }

  @EventHandler
  fun on(e: OrderClosedEvent) {
    val order = findOrder(e.orderId)
    if (order == null) {
      logger.error { "Order not found $e" }
      return
    }
    order.orderStatus = OrderStatus.CLOSED
  }

  @EventHandler
  fun on(e: OrderPlacedEvent) {
    val order = findOrder(e.orderId)
    if (order == null) {
      logger.error { "Order not found $e" }
      return
    }
    order.orderStatus = OrderStatus.PLACED
  }

  @EventHandler
  fun on(e: OrderDeliveredEvent) {
    val order = findOrder(e.orderId)
    if (order == null) {
      logger.error { "Order not found $e" }
      return
    }
    order.orderStatus = OrderStatus.DELIVERED
  }

  @EventHandler
  fun on(e: MealAddedEvent) {
    val order = findOrder(e.orderId)
    if (order == null) {
      logger.error { "Order not found $e" }
      return
    }

    order.meals[e.guestName] = e.meal
  }

  @EventHandler
  fun on(e: PaymentAddedEvent) {
    val order = findOrder(e.orderId)
    if (order == null) {
      logger.error { "Order not found $e" }
      return
    }

    order.payments[e.guestName] = e.amount
  }

  fun findOrder(orderId: OrderId): OrderDto? = this.orders.find { it.orderId == orderId }

}

data class OrderDto(
  val orderId: OrderId,
  var orderStatus: OrderStatus,
  val meals: MutableMap<String, Meal> = mutableMapOf(),
  val payments: MutableMap<String, Amount> = mutableMapOf()
)
