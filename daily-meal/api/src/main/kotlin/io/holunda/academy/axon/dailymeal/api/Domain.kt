package io.holunda.academy.axon.dailymeal.api

import java.math.BigDecimal

/**
 * Order id.
 */
inline class OrderId(val value: String) {
  companion object {
    val UNDEF: OrderId = OrderId("")
  }
}

/**
 * Currency representation.
 */
inline class Currency(val value: String) {
  companion object {
    val EUR: Currency = Currency("EUR")
  }
}

/**
 * Order status.
 */
enum class OrderStatus {
  OPEN,
  CLOSED,
  PLACED,
  DELIVERED
}

/**
 * Amount (value with currency).
 */
data class Amount(
  val amount: BigDecimal,
  val currency: Currency = Currency.EUR
) {
  /**
   * Returns true, if the provided amount is bigger than this (only by equal currency).
   */
  fun isCapped(amount: Amount): Boolean = amount.currency == this.currency && amount.amount >= this.amount
}

/**
 * Represents a meal.
 */
data class Meal(
  val name: String,
  val amount: Amount
)
