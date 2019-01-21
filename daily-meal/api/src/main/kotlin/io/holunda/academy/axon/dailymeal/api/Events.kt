package io.holunda.academy.axon.dailymeal.api

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
