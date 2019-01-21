package io.holunda.academy.axon.dailymeal.core

import io.holunda.academy.axon.dailymeal.api.*
import org.axonframework.test.aggregate.AggregateTestFixture
import org.axonframework.test.aggregate.FixtureConfiguration
import org.axonframework.test.aggregate.TestExecutor
import org.junit.Before
import org.junit.Test
import java.lang.IllegalStateException

class OrderAggregateTest {

  lateinit var fixture: FixtureConfiguration<OrderAggregate>

  @Before
  fun before() {
    fixture = AggregateTestFixture(OrderAggregate::class.java)
  }

  @Test
  fun `should create order`() {
    fixture
      .givenNoPriorActivity()
  }
}


fun <T> TestExecutor<T>.whenever(command: Any) = this.`when`(command)
fun <T> TestExecutor<T>.whenever(command: Any, metaData: Map<String, Any>) = this.`when`(command, metaData)
