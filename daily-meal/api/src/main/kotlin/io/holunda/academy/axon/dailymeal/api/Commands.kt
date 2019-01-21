package io.holunda.academy.axon.dailymeal.api

import org.axonframework.modelling.command.TargetAggregateIdentifier

data class OpenOrderCommand(
  @TargetAggregateIdentifier
  val orderId: String,
  val providerName: String,
  val creator: String
)
