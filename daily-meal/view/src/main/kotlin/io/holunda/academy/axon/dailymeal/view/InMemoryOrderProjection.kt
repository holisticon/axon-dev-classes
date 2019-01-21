package io.holunda.academy.axon.dailymeal.view

import io.holunda.academy.axon.dailymeal.api.*
import org.springframework.core.annotation.Order
import org.springframework.stereotype.Component

@Component
class InMemoryOrderProjection() {

  private val orders: MutableSet<Order> = mutableSetOf()

  fun on(e: OrderOpenedEvent) {

  }

  fun on(e: OrderClosedEvent) {

  }

  fun on(e: OrderPlacedEvent) {

  }

  fun on(e: OrderDeliveredEvent) {

  }

  fun on(e: MealAddedEvent) {

  }

  fun on(e: PaymentAddedEvent) {

  }

}

data class OrderDto(
  val orderId: OrderId,
  val orderStatus: OrderStatus,
  val meals: MutableMap<String, Meal> = mutableMapOf(),
  val payments: MutableMap<String, Amount> = mutableMapOf()
)
