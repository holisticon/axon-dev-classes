package io.holunda.academy.axon.dailymeal.core

import io.holunda.academy.axon.dailymeal.api.*
import org.axonframework.commandhandling.CommandHandler
import org.axonframework.eventsourcing.EventSourcingHandler
import org.axonframework.modelling.command.AggregateIdentifier
import org.axonframework.modelling.command.AggregateLifecycle
import org.axonframework.spring.stereotype.Aggregate

@Aggregate
class OrderAggregate {

  @AggregateIdentifier
  private var orderId: OrderId
  private var status: OrderStatus

  private lateinit var providerName: String
  private lateinit var creatorName: String
  private var meals: MutableMap<String, Meal> = mutableMapOf()
  private var payments: MutableMap<String, Amount> = mutableMapOf()

  constructor() {
    this.orderId = OrderId.UNDEF
    this.status = OrderStatus.OPEN
  }

  @CommandHandler
  constructor(command: OpenOrderCommand): this() {
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
    }
  }

  @CommandHandler
  fun placeOrder(command: CloseOrderCommand) {
    if (this.status == OrderStatus.CLOSED && orderBalanced()) {
      AggregateLifecycle.apply(OrderPlacedEvent(
        orderId = this.orderId,
        providerName = this.providerName,
        meals = this.meals.map { entry -> entry.value }.toSet()
      ))
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

  @EventSourcingHandler
  fun on(event: OrderOpenedEvent) {
    this.orderId = event.orderId
    this.providerName = event.providerName
    this.creatorName = event.creatorName
  }

  @EventSourcingHandler
  fun on(event: OrderClosedEvent) {
    this.status = OrderStatus.CLOSED
  }

  @EventSourcingHandler
  fun on(event: OrderPlacedEvent) {
    this.status = OrderStatus.PLACED
  }

  @EventSourcingHandler
  fun on(event: OrderDeliveredEvent) {
    this.status = OrderStatus.DELIVERED
  }


  fun orderBalanced(): Boolean {
    meals.forEach{
      if (!payments.containsKey(it.key) || payments[it.key] != it.value.amount) {
        return false
      }
    }
    return true
  }

}
