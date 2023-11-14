# HW3- Manual Review
Hi there! Welcome to Yujin and Yuqi's Expense Tracker App!
This README.md file is inherited from HW2, every single new functionality would be explained in New Functionality section.

The homework will be based on this project named "Expense Tracker",where users will be able to add/remove daily transaction. 

## Compile

To compile the code from terminal, use the following command:
```
cd src
javac ExpenseTrackerApp.java
java ExpenseTrackerApp
```

You should be able to view the GUI of the project upon successful compilation. 


## How to use

After the application is compiled, you will see a panel with input fields and buttons. 

On the top, you can enter the amounts and categories of your transactions, click "Add Transaction" button to add your transaction to current central panel. If you find yourself enter something run and want to revoke the transaction, feel free to click "Undo" button to cancel your addition. **You can also delete many transactions together in one time!**

If you want to know what transaction do you have between a certain amount, or what transaction do you have with a certain category, you can use "Filter by amount" button and "Filter by category" button. Transactions that fulfill the conditions will be highlighted in light green.

Hope you have a great time playing with this application!



## New Files

UndoException.java

This file is ued for the undoTransaction funtion to throw exception. Then in the test, we can use try{}catch{} to get the error message, in order to test if this functionality works as expected.


# New Functionalities

## Model

In order to delete transactions, in the ExpenseTrackerModel.java, removeTransaction(Transaction t) was added to delete transactions and it's called in the controller.

## View

When users click any transactions that are allowed to be deleted, the background will turn to blue to tell users these transactions are allowed to be deleted. Users are able to undo one single transaction or many transactions together.

## Controller

The undoTransaction() function was created for revoking and deleting selected transactions. It takes an array of int, which is the selected rows, as parameter, looping over all of the current transactions, deleting the selected transactions.


## Testing

The first one tests the add transaction functionality without using any functions in Model or Controller.

The second one tests if the inputs are valid by comparing expected error message when entering invalid inputs on purpose and the error message thrown by program itself. This test involves Model.

The third one tests amount filter by comparing the expected number of selected rows after applying amount filter and the real number of selected rows. 

The fourth one does the same thing as the third one but tests the category filter.

The fifth one tests diffent situations that undo is is not allowed, different error message should be recieved in different disallowd undo situation.

The last one is testing if users are allowed to undo transactions. This test is accomplished by checking amounts and categories for each row after applying deleting functionality.

## Java Version
This code is compiled with ```openjdk 17.0.7 2023-04-18```. Please update your JDK accordingly if you face any incompatibility issue.