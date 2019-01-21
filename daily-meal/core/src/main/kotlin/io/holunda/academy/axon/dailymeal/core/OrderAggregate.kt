package io.holunda.academy.axon.dailymeal.core

import io.holunda.academy.axon.dailymeal.api.*
import org.axonframework.commandhandling.CommandHandler
import org.axonframework.eventsourcing.EventSourcingHandler
import org.axonframework.modelling.command.AggregateIdentifier
import org.axonframework.modelling.command.AggregateLifecycle
import org.axonframework.spring.stereotype.Aggregate

@Aggregate
class OrderAggregate() {

  @AggregateIdentifier
  lateinit var orderId: String
  lateinit var status: OrderStatus

  lateinit var providerName: String
  lateinit var creatorName: String
  var meals: MutableMap<String, Meal> = mutableMapOf()

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

  @EventSourcingHandler
  fun on(event: OrderOpenedEvent) {
    this.status = OrderStatus.OPEN
    this.orderId = event.orderId.value
    this.providerName = event.providerName
    this.creatorName = event.creatorName
  }



}
