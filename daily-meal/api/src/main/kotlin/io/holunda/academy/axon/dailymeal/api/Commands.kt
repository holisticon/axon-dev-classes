package io.holunda.academy.axon.dailymeal.api

import org.axonframework.modelling.command.TargetAggregateIdentifier

/**
 * Creates the meal order.
 */
data class OpenOrderCommand(
  @TargetAggregateIdentifier
  val orderId: OrderId,
  val providerName: String,
  val creatorName: String
)
