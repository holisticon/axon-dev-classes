package io.holunda.academy.axon.dailymeal.api

import java.math.BigDecimal

interface OrderEvent {
  val orderId: OrderId
}

data class OrderOpenedEvent(
  override val orderId: OrderId,
  val providerName: String,
  val creatorName: String
) : OrderEvent

data class OrderClosedEvent(
  override val orderId: OrderId
) : OrderEvent

data class OrderPlacedEvent(
  override val orderId: OrderId,
  val meals: Set<Meal>,
  val providerName: String
) : OrderEvent

data class OrderDeliveredEvent(
  override val orderId: OrderId
) : OrderEvent


data class MealAddedEvent(
  override val orderId: OrderId,
  val guestName: String,
  val selfPaying: Boolean,
  val meal: Meal
) : OrderEvent

data class PaymentAddedEvent(
  override val orderId: OrderId,
  val guestName: String,
  val amount: BigDecimal,
  val currency: Currency = Currency.EUR
) : OrderEvent
