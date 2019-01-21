# Axon Developer Classes

## Clone and checkout

To get your first copy, please enter

    git clone https://github.com/holisticon/axon-dev-classes

Every task covered in the workshop is supported by a lab class. The `class/branch X` contains the solution for the class X. To start, checkout `master` and start with `class 1`. After you are done, checkout the branch `class/1-core-api` and inspect the solution.

The following branches exist:

    master
    class/1-core-api

Please change the branches using:

    git stash
    # or to keep yours
    git add --all
    git commit -m "my solution"
    # then
    git checkout class/<class-name>

## Building

In order to build the project, please enter

    ./mvnw clean install

##  Running examples

In order to run the project, please enter

    ./mvnw spring-boot:run

# Use case

Today, a single person is involved in the meal ordering process, collects the meal orders 
from people in the office, collects the money and places the orders (sometimes multiple) 
by the local meal providers. The new system should be able to automate the order creation. 
Everyone is capable to see orders created today and join to the order by placing the meal wish. 
Orders are created per meal provider. After all meals are added, the order can be closed. 
The order creator collects all the money for the order and places the order by the provider.

In the classes, the system without a UI but with a REST interface will be developed. 

# Lab Classes 

## Class 1: Core API design

Identify the public API of the system. Start with commands and queries. 
What events should be emitted? Create data classes for commands, 
queries and events in `daily-meal-api` module.

## Class 2: Test it

Implement an aggregate test in `core` module, which tests the `OrderAggregate`. 
Use `FixtureConfiguration<OrderAggregate>` to initialize the test with
`AggregateTestFixture(OrderAggregate::class)` as value.

Write the specification how the aggregate should behave if `OpenOrderCommand` 
is received. The test should remain failing at the end of the task.

## Class 3: Aggregate design

Implement the `OrderAggregate` in `core` module which is created 
on `OpenOrderCommand`. Use an aggregate member `orderId` of type 
`OrderId` to identify the aggregate (mark it with `@AggregateIdentifier`). 

Create a constructor annotated by the `@CommandHandler` and use 
`AggregateLifecycle.apply()` to emit events.

Store at least the aggregate identifier in the sourcing event handler
(an aggregate method marked with `@EventSourcingHandler`).

## Class 4: Projection

Implement a simple view projection (`InMemoryOrderProjection`) in `view` module.

 


