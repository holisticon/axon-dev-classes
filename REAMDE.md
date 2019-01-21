# Axon Developer Classes

## Clone and checkout

To get your first copy, please enter

    git clone https://github.com/holisticon/camunda-dev-classes.git

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

A new system for handling daily meal ordering in a small company needs to be created. Today, a single person is involved in the ordering process, collects the meal orders from people in the office, collects the money and places the orders (sometimes multiple) by the local meal providers.
The new system should be able to automate the order creation. Everyone is capable to see orders created today and join to the order by placing the meal wish. Orders are created per meal provider. For a meal added to the order, the user indicates the amount it is willing to pay (including tip). After all meals are added, the order can be closed. The order creator collects all the money for the order and marks the money for meals as collected.
In the classes, the system without a UI but with a REST interface will be developed. Checkout this repository: 

# Lab Classes 

## Class 1: Core API design

### Task 1

Identify the public API of the system. Start with commands and queries. What events should be emmited?

### Task 2

Create data classes for commands, queries and events in `daily-meal-api` module.

