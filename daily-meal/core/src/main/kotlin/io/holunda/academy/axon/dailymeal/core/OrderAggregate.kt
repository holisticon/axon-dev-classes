package io.holunda.academy.axon.dailymeal.core

import io.holunda.academy.axon.dailymeal.api.*
import org.axonframework.commandhandling.CommandHandler
import org.axonframework.eventsourcing.EventSourcingHandler
import org.axonframework.modelling.command.AggregateIdentifier
import org.axonframework.modelling.command.AggregateLifecycle
import org.axonframework.spring.stereotype.Aggregate

@Aggregate
@Suppress("unused")
open class OrderAggregate {

  @AggregateIdentifier
  private var orderId: OrderId = OrderId.UNDEF
  private lateinit var status: OrderStatus
  private lateinit var creatorName: String
  private lateinit var providerName: String
  private val meals: MutableMap<String, Meal> = mutableMapOf()
  private val payments: MutableMap<String, Amount> = mutableMapOf()

  constructor() {
    this.orderId = OrderId.UNDEF
  }

  @CommandHandler
  constructor(command: OpenOrderCommand) : this() {
    AggregateLifecycle.apply(
      OrderOpenedEvent(
        orderId = command.orderId,
        creatorName = command.creatorName,
        providerName = command.providerName
      )
    )
  }

  @CommandHandler
  fun closeOrder(command: CloseOrderCommand) {
    if (this.status == OrderStatus.OPEN) {
      AggregateLifecycle.apply(OrderClosedEvent(
        this.orderId
      ))
    } else {
      throw IllegalStateException("Order must be opened, current state was $status")
    }
  }

  @CommandHandler
  fun placeOrder(command: PlaceOrderCommand) {
    if (this.status == OrderStatus.CLOSED) {
      if (meals.isEmpty()) {
        throw IllegalStateException("Order must contain meals. Current order is empty.")
      }

      if (orderCapped()) {
        AggregateLifecycle.apply(OrderPlacedEvent(
          orderId = this.orderId,
          providerName = this.providerName,
          meals = this.meals.map { entry -> entry.value }.toSet()
        ))
      } else {
        throw IllegalStateException("Order must be capped, currently there are meals without payments.")
      }
    } else {
      throw IllegalStateException("Order must be closed, current state was $status")
    }
  }

  @CommandHandler
  fun markOrderDelivered(command: MarkOrderDeliveredCommand) {
    if (this.status == OrderStatus.PLACED) {
      AggregateLifecycle.apply(OrderDeliveredEvent(
        this.orderId
      ))
    }
  }

  @CommandHandler
  fun addMeal(command: AddMealCommand) {
    if (this.status == OrderStatus.OPEN) {
      if (this.meals.containsKey(command.guestName)) {
        throw IllegalStateException("The use ${command.guestName} already ordered a meal by this provider: ${this.meals[command.guestName]}")
      }
      AggregateLifecycle.apply(MealAddedEvent(orderId = this.orderId, guestName = command.guestName, meal = command.meal))
    }
  }

  @CommandHandler
  fun addPaymant(command: PayMealCommand) {
    if (this.status == OrderStatus.CLOSED) {
      if (this.payments.containsKey(command.guestName)) {
        throw IllegalStateException("The use ${command.guestName} already placed a payment: ${this.meals[command.guestName]}")
      }
      AggregateLifecycle.apply(PaymentAddedEvent(orderId = this.orderId, guestName = command.guestName, amount = command.amount))
    } else {
      throw IllegalStateException("Order must be closed, current state was $status")
    }
  }


  @EventSourcingHandler
  open fun on(event: OrderOpenedEvent) {
    this.orderId = event.orderId
    this.providerName = event.providerName
    this.creatorName = event.creatorName
    this.status = OrderStatus.OPEN
  }

  @EventSourcingHandler
  open fun on(event: OrderClosedEvent) {
    this.status = OrderStatus.CLOSED
  }

  @EventSourcingHandler
  open fun on(event: OrderPlacedEvent) {
    this.status = OrderStatus.PLACED
  }

  @EventSourcingHandler
  open fun on(event: OrderDeliveredEvent) {
    this.status = OrderStatus.DELIVERED
  }

  @EventSourcingHandler
  open fun on(event: MealAddedEvent) {
    this.meals[event.guestName] = event.meal
  }

  @EventSourcingHandler
  open fun on(event: PaymentAddedEvent) {
    this.payments[event.guestName] = event.amount
  }

  private fun orderCapped(): Boolean = findUnpaidMeals().isEmpty()

  private fun findUnpaidMeals() = this.meals
    .filter { (name, meal) -> !this.payments.containsKey(name) || !meal.amount.isCapped(this.payments[name]!!) }
}
