package io.holunda.academy.axon.dailymeal.api

interface OrderEvent {
  val orderId: String
}

data class OrderOpenedEvent(
  override val orderId: String,
  val providerName: String,
  val creator: String
) : OrderEvent
