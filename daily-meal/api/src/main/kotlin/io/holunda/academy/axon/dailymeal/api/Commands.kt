package io.holunda.academy.axon.dailymeal.api

import org.axonframework.modelling.command.TargetAggregateIdentifier
import java.math.BigDecimal

/**
 * Creates the meal order.
 */
data class OpenOrderCommand(
  @TargetAggregateIdentifier
  val orderId: OrderId,
  val providerName: String,
  val creatorName: String
)

/**
 * Sends the order details to the provider.
 */
data class PlaceOrderCommand(
  @TargetAggregateIdentifier
  val orderId: OrderId
)

/**
 * Marks the order as delivered.
 */
data class MarkOrderDeliveredCommand(
  @TargetAggregateIdentifier
  val orderId: OrderId
)

/**
 * Adds a meal to existing order.
 */
data class AddMealCommand(
  @TargetAggregateIdentifier
  val orderId: OrderId,
  val guestName: String,
  val meal: Meal,
  val selfPaying: Boolean = true
)

/**
 * Adds a payment to existing order.
 */
data class AddPaymentCommand(
  @TargetAggregateIdentifier
  val orderId: OrderId,
  val guestName: String,
  val amount: BigDecimal,
  val currency: Currency = Currency.EUR
)



