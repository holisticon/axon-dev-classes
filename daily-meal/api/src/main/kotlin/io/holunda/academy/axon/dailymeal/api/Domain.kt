package io.holunda.academy.axon.dailymeal.api

import java.math.BigDecimal


inline class OrderId(val value: String)

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


/**
 * Represents a meal.
 */
data class Meal(
  val name: String,
  val price: BigDecimal,
  val currency: Currency = Currency.EUR
)
