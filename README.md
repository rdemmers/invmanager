# Inventory Manager

This is an application that I'm currently building for a medium sized company. This git exists purely as github workflow practice and as a portfolio project for future employers. Since some elements of the application are company specific I'm unable to upload the whole project. Therefore it won't compile straight from this repository.

## Functionality

The application is built in Spring MVC, with a front-end build in React (see my other repository). I made the transition from Apache Tiles/ JSP to React/Redux recently so some functionality that existed earlier has been temporarily removed. 

### Explanation of the application

The Inventory manager is an API-based application. It handles product mutation, suppliers and ordering. 

##### The following functionality is included at this moment:

* Protected through a log-in system before you receive the React front application
* Products can be:
  * Added
  * Removed
  * Updated
* Products have a Stock property and a minimumStock property. Once a product gets below the given minimum, it'll be added to a 'Low product list', from which the product can easily be ordered by only supplying the amount you wish to order.
* Once a product has been placed on the order list by giving giving the amount you wish to order, the application automatically generates e-mails and sends them to the right supplier (WIP, almost finished)
* Once an order is received, it takes one click to add it to the inventory and finish up the order. A history of received orders remains.

##### The following functions that were available will be added within the next month:

* Registering new users from within the application
* An admin panel/dashboard to adjust settings within the application
* A page containing statistics about products, orders and suppliers

Once these functions have been added I will upload a demo version here.

### Future plans

* Constantly optimizing and cleaning the existing code, I've got a long way to go
* Adding integration for work instruction
* Upgrading the application from an inventory manager to a complete project manager
