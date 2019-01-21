package io.holunda.academy.axon.dailymeal.api

import java.math.BigDecimal

/**
 * Event relevant for an order.
 */
interface OrderEvent {
  val orderId: OrderId
}

/**
 * New order is opened.
 */
data class OrderOpenedEvent(
  override val orderId: OrderId,
  val providerName: String,
  val creatorName: String
) : OrderEvent

/**
 * Order is closed.
 */
data class OrderClosedEvent(
  override val orderId: OrderId
) : OrderEvent

/**
 * Order is placed.
 */
data class OrderPlacedEvent(
  override val orderId: OrderId,
  val meals: Set<Meal>,
  val providerName: String
) : OrderEvent

/**
 * Order is delivered.
 */
data class OrderDeliveredEvent(
  override val orderId: OrderId
) : OrderEvent

/**
 * Meal is added.
 */
data class MealAddedEvent(
  override val orderId: OrderId,
  val guestName: String,
  val meal: Meal
) : OrderEvent

/**
 * Payment is added.
 */
data class PaymentAddedEvent(
  override val orderId: OrderId,
  val guestName: String,
  val amount: Amount
) : OrderEvent
