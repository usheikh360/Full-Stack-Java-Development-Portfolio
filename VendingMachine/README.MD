# Vending Machine

This goal of this project was to create an apllication that simulates a vending machine.

# The Application demonstrates an understanding of:

• MVC pattern (App class, Controller, View, Service Layer, DAO) – this includes the use of constructor based dependency injection)

• A full set of unit tests for your DAO and Service Layer components

• Use of BigDecimal for all monetary calculations where applicable

• Use of application specific exceptions. The following application specific exceptions are thrown by the Service Layer:
   One that is thrown when the user tries to purchase an item but doesn’t deposit enough money (i.e. InsufficientFundsException).
   One that is thrown when the user tries to purchase an item that has zero inventory (i.e. NoItemInventoryException).
  
• Use of enums to represent the values of different coins

• Use of an Audit DAO to log events and the time they occurred

# The Application has the following features:

• The application displays all of the items and their respective prices when the application starts, along with an option to exit the application.

• The user has to put in some amount of money before an item can be selected.

• Only one item can be vended at a time.

• If the user selects an item that costs more than the amount the user put into the vending machine, the program displays a message indicating insufficient funds and then redisplay the amount the user had put into the machine.

• If the user selects an item that costs equal to or less than the amount of money that the user put in the vending machine, the program displays the change returned to the user.

• Vending machine items are stored in a file.  Inventory for the vending machine is read from this file when the program starts and is written out to this file just before the program exits.  
  The program tracks the following properties for each item: Item name,  Item cost, Number of items in inventory.

• When an item is vended, the program updates the inventory level appropriately.  If the machine runs out of an item, it is no longer be available as an option to the user.  However, the items that have an inventory level of zero are still read from and written to the inventory file and stored in memory


