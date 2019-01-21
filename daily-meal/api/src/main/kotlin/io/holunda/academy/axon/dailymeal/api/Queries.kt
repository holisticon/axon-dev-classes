package io.holunda.academy.axon.dailymeal.api

/**
 * Shows orders.
 */
data class OrderQuery(
  val status: OrderStatus = OrderStatus.OPEN
)

/**
 * Shows open payments.
 */
data class OpenPaymentQuery(
  val orderId: OrderId?,
  val user: String?
)
