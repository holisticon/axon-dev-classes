package io.holunda.academy.axon.dailymeal.api

import java.math.BigDecimal

interface OrderEvent {
  val orderId: String
}

data class OrderOpenedEvent(
  override val orderId: String,
  val providerName: String,
  val creator: String
) : OrderEvent

data class OrderPlacedEvent(
  override val orderId: String,
  val meals: Set<Meal>,
  val providerName: String
) : OrderEvent

data class OrderDeliveredEvent(
  override val orderId: String
) : OrderEvent


data class MealAddedEvent(
  override val orderId: String,
  val guestName: String,
  val selfPaying: Boolean,
  val meal: Meal
) : OrderEvent

data class PaymentAddedEvent(
  override val orderId: String,
  val guestName: String,
  val amount: BigDecimal,
  val currency: Currency = Currency.EUR
) : OrderEvent
