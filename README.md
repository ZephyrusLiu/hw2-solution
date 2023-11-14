# hw2
# Hi there! Welcome to Yujin and Yuqi's Expense Tracker App!
# This README.md file is inherited from HW2, every single new functionality would be explained in New Functionality section.

## How to use

First, you are able to run "javac ExpenseTrackerApp.java" and "java ExpenseTrackerApp" to launch the application. Then, you will see a panel with input fields and buttons. 

On the top, you can enter the amounts and categories of your transactions, click "Add Transaction" button to add your transaction to current central panel. If you find yourself enter something run and want to revoke the transaction, feel free to click "Undo" button to cancel your addition. You can also delete many transactions together in one time! 

If you want to know what transaction do you have between a certain amount, or what transaction do you have with a certain category, you can use "Filter by amount" button and "Filter by category" button. Transactions that fulfill the conditions will be highlighted in light green.

Hope you have a great time playing with this application!



## New Files

#TODO



## New Functionalities

# Model

In order to delete transactions, in the ExpenseTrackerModel.java, removeTransaction(Transaction t) was added to delete transactions and it's called in the controller.

# View

When users click any transactions that are allowed to be deleted, the background will turn to blue to tell users these transactions are allowed to be deleted. Users are able to undo one single transaction or many transactions together.

# Controller

The undoTransaction() function was created for revoking and deleting selected transactions. It takes an array of int, which is the selected rows, as parameter, looping over all of the current transactions, deleting the selected transactions.



# Testing

The first one is testing the add transaction functionality without using any functions in Model or Controller.

The second one is testing if the inputs are valid by comparing expected error message when entering invalid inputs on purpose and the error message thrown by program itself. This test involves Model.

The third one is testing amount filter by comparing the expected number of selected rows after applying amount filter and the real number of selected rows. The fourth one does the same thing as well.

#TODO

The last one is testing if users are allowed to undo transactions. This test is accomplished by checking amounts and categories for each row after applying deleting functionality.