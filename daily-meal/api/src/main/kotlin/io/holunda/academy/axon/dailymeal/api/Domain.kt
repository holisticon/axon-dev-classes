package io.holunda.academy.axon.dailymeal.api

import java.math.BigDecimal


inline class OrderId(val value: String) {
  companion object {
    val UNDEF: OrderId = OrderId("")
  }
}

inline class Currency(val value: String) {
  companion object {
      val EUR: Currency = Currency("EUR")
  }
}

enum class OrderStatus {
  OPEN,
  CLOSED,
  PLACED,
  DELIVERED
}

data class Amount(val amount: BigDecimal, val currency: Currency = Currency.EUR)

/**
 * Represents a meal.
 */
data class Meal(
  val name: String,
  val amount: Amount
)
