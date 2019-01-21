package io.holunda.academy.axon.dailymeal.core

import io.holunda.academy.axon.dailymeal.api.*
import org.axonframework.commandhandling.CommandHandler
import org.axonframework.eventsourcing.EventSourcingHandler
import org.axonframework.modelling.command.AggregateIdentifier
import org.axonframework.modelling.command.AggregateLifecycle
import org.axonframework.spring.stereotype.Aggregate

@Aggregate
class OrderAggregate {

  constructor() {
  }

}
