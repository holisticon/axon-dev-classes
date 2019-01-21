package io.holunda.academy.axon.dailymeal.core

import io.holunda.academy.axon.dailymeal.api.*
import org.axonframework.test.aggregate.AggregateTestFixture
import org.axonframework.test.aggregate.FixtureConfiguration
import org.axonframework.test.aggregate.TestExecutor
import org.junit.Before
import org.junit.Test
import java.math.BigDecimal

class OrderAggregateTest {

  lateinit var fixture: FixtureConfiguration<OrderAggregate>

  @Before
  fun before() {
    fixture = AggregateTestFixture(OrderAggregate::class.java)
  }

  private val orderId = OrderId("4711")

  private val providerName = "Mundfein"

  private val name = "kermit"

  @Test
  fun `should create order`() {
    fixture
      .givenNoPriorActivity()
      .whenever(
        OpenOrderCommand(orderId, providerName, name))
      .expectEvents(
        OrderOpenedEvent(orderId, providerName, name))
  }

  @Test
  fun `should close opened order`() {
    fixture
      .given(
        OrderOpenedEvent(orderId, providerName, name)
      ).whenever(
        CloseOrderCommand(orderId)
      ).expectEvents(
        OrderClosedEvent(orderId)
      )
  }

  @Test
  fun `should not close closed order`() {
    fixture
      .given(
        OrderOpenedEvent(orderId, providerName, name),
        OrderClosedEvent(orderId)
      ).whenever(
        CloseOrderCommand(orderId)
      ).expectExceptionMessage("Order must be opened, current state was CLOSED")
  }

  @Test
  fun `should not place empty closed order`() {
    fixture
      .given(
        OrderOpenedEvent(orderId, providerName, name),
        OrderClosedEvent(orderId)
      ).whenever(
        PlaceOrderCommand(orderId)
      ).expectExceptionMessage(
        "Order must contain meals. Current order is empty."
      )
  }

  @Test
  fun `should not place order if order not capped`() {
    val meal = Meal("Noodles", Amount(BigDecimal("7.95")))
    fixture
      .given(
        OrderOpenedEvent(orderId, providerName, name),
        OrderClosedEvent(orderId),
        MealAddedEvent(orderId, name, meal)
      ).whenever(
        PlaceOrderCommand(orderId)
      ).expectExceptionMessage("Order must be capped, currently there are meals without payments.")
  }

  @Test
  fun `should place order if order is capped`() {
    val meal = Meal("Noodles", Amount(BigDecimal("7.95")))

    fixture
      .given(
        OrderOpenedEvent(orderId, providerName, name),
        OrderClosedEvent(orderId),
        MealAddedEvent(orderId, name, meal),
        PaymentAddedEvent(orderId, name, Amount(BigDecimal("10.00")))
      ).whenever(
        PlaceOrderCommand(orderId)
      ).expectEvents(
        OrderPlacedEvent(orderId, setOf(meal), providerName)
      )
  }


  @Test
  fun `should not place placed order`() {
    fixture
      .given(
        OrderOpenedEvent(orderId, providerName, name),
        OrderClosedEvent(orderId),
        OrderPlacedEvent(orderId, setOf(), providerName)
      ).whenever(
        PlaceOrderCommand(orderId)
      ).expectExceptionMessage("Order must be closed, current state was PLACED")
  }

  @Test
  fun `should deliver placed order`() {
    fixture
      .given(
        OrderOpenedEvent(orderId, providerName, name),
        OrderClosedEvent(orderId),
        OrderPlacedEvent(orderId, setOf(), providerName)
      ).whenever(
        MarkOrderDeliveredCommand(orderId)
      ).expectEvents(
        OrderDeliveredEvent(orderId)
      )
  }


  @Test
  fun `should not deliver delivered order`() {
    fixture
      .given(
        OrderOpenedEvent(orderId, providerName, name),
        OrderClosedEvent(orderId),
        OrderPlacedEvent(orderId, setOf(), providerName),
        OrderDeliveredEvent(orderId)
      ).whenever(
        MarkOrderDeliveredCommand(orderId)
      ).expectExceptionMessage("Order must be placed, current state was DELIVERED")
  }


}


fun <T> TestExecutor<T>.whenever(command: Any) = this.`when`(command)
fun <T> TestExecutor<T>.whenever(command: Any, metaData: Map<String, Any>) = this.`when`(command, metaData)
