package controller;

import view.ExpenseTrackerView;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import model.ExpenseTrackerModel;
import model.Transaction;
import model.Filter.TransactionFilter;

public class ExpenseTrackerController {

  private ExpenseTrackerModel model;
  private ExpenseTrackerView view;
  /**
   * The Controller is applying the Strategy design pattern.
   * This is the has-a relationship with the Strategy class
   * being used in the applyFilter method.
   */
  private TransactionFilter filter;

  public ExpenseTrackerController(ExpenseTrackerModel model, ExpenseTrackerView view) {
    this.model = model;
    this.view = view;
  }

  public void setFilter(TransactionFilter filter) {
    // Sets the Strategy class being used in the applyFilter method.
    this.filter = filter;
  }

  public void refresh() {
    List<Transaction> transactions = model.getTransactions();
    view.refreshTable(transactions);
  }

  public boolean addTransaction(double amount, String category) {
    if (!InputValidation.isValidAmount(amount)) {
      return false;
    }
    if (!InputValidation.isValidCategory(category)) {
      return false;
    }

    Transaction t = new Transaction(amount, category);
    model.addTransaction(t);
    view.getTableModel().addRow(new Object[] { t.getAmount(), t.getCategory(), t.getTimestamp() });
    refresh();
    return true;
  }

  public void applyFilter() {
    // null check for filter
    if (filter != null) {
      // Use the Strategy class to perform the desired filtering
      List<Transaction> transactions = model.getTransactions();
      List<Transaction> filteredTransactions = filter.filter(transactions);
      List<Integer> rowIndexes = new ArrayList<>();
      for (Transaction t : filteredTransactions) {
        int rowIndex = transactions.indexOf(t);
        if (rowIndex != -1) {
          rowIndexes.add(rowIndex);
        }
      }
      view.highlightRows(rowIndexes);
    } else {
      JOptionPane.showMessageDialog(view, "No filter applied");
      view.toFront();
    }

  }

  // Add undo button function
  // throw exception use UndoException
  public void undoTransaction(int[] selectedRows) throws UndoException {
    // Get transaction list.
    List<Transaction> transactions = model.getTransactions();

    // Count current number of rows in the table.
    int rowCount = view.getTransactionsTable().getRowCount();

    // If the list is empty, there is no transaction
    if (rowCount < 2) {
      throw new UndoException("This undo is not allowed: The transaction list is empty.");
    }

    // remove all the selected rows
    else if (selectedRows.length > 0 && selectedRows.length <= transactions.size()) {
      for (int selectedRow : selectedRows) {
        Transaction selectedTransaction = transactions.get(selectedRow);
        model.removeTransaction(selectedTransaction);
      }
      // refresh table after undo to show the updated table, which has updated list and cost.
      view.refreshTable(model.getTransactions());

    } else {
      // When list is not empty && no transaction is selected
      throw new UndoException("This undo is not allowed: No transaction is selected.");
    }
  }
}
