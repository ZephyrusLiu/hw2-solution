
// package test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.Date;
import java.util.List;

import javax.swing.JFormattedTextField;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import java.text.ParseException;

import org.junit.Before;
import org.junit.Test;

import controller.ExpenseTrackerController;
import model.ExpenseTrackerModel;
import model.Transaction;
import model.Filter.AmountFilter;
import model.Filter.CategoryFilter;
import view.ExpenseTrackerView;

public class TestExample {

    private ExpenseTrackerModel model;
    private ExpenseTrackerView view;
    private ExpenseTrackerController controller;

    @Before
    public void setup() {
        model = new ExpenseTrackerModel();
        view = new ExpenseTrackerView();
        controller = new ExpenseTrackerController(model, view);
    }

    public double getTotalCost() {
        double totalCost = 0.0;
        List<Transaction> allTransactions = model.getTransactions(); // Using the model's getTransactions method
        for (Transaction transaction : allTransactions) {
            totalCost += transaction.getAmount();
        }
        return totalCost;
    }

    public void checkTransaction(double amount, String category, Transaction transaction) {
        assertEquals(amount, transaction.getAmount(), 0.01);
        assertEquals(category, transaction.getCategory());
        String transactionDateString = transaction.getTimestamp();
        Date transactionDate = null;
        try {
            transactionDate = Transaction.dateFormatter.parse(transactionDateString);
        } catch (ParseException pe) {
            pe.printStackTrace();
            transactionDate = null;
        }
        Date nowDate = new Date();
        assertNotNull(transactionDate);
        assertNotNull(nowDate);
        // They may differ by 60 ms
        assertTrue(nowDate.getTime() - transactionDate.getTime() < 60000);
    }

    @Test
    public void testAddTransaction() {
        // Pre-condition: List of transactions is empty
        assertEquals(0, model.getTransactions().size());

        // Perform the action: Add a transaction
        double amount = 50.0;
        String category = "food";
        assertTrue(controller.addTransaction(amount, category));

        // Post-condition: List of transactions contains only
        // the added transaction
        assertEquals(1, model.getTransactions().size());

        // Check the contents of the list
        Transaction firstTransaction = model.getTransactions().get(0);
        checkTransaction(amount, category, firstTransaction);

        // Check the total amount
        assertEquals(amount, getTotalCost(), 0.01);
    }

    @Test
    public void testRemoveTransaction() {
        // Pre-condition: List of transactions is empty
        assertEquals(0, model.getTransactions().size());

        // Perform the action: Add and remove a transaction
        double amount = 50.0;
        String category = "food";
        Transaction addedTransaction = new Transaction(amount, category);
        model.addTransaction(addedTransaction);

        // Pre-condition: List of transactions contains only
        // the added transaction
        assertEquals(1, model.getTransactions().size());
        Transaction firstTransaction = model.getTransactions().get(0);
        checkTransaction(amount, category, firstTransaction);

        assertEquals(amount, getTotalCost(), 0.01);

        // Perform the action: Remove the transaction
        model.removeTransaction(addedTransaction);

        // Post-condition: List of transactions is empty
        List<Transaction> transactions = model.getTransactions();
        assertEquals(0, transactions.size());

        // Check the total cost after removing the transaction
        double totalCost = getTotalCost();
        assertEquals(0.00, totalCost, 0.01);
    }

    @Test
    public void testAddTransactionInView() {
        // Transaction is empty before test
        assertEquals(0, view.getTransactionsTable().getRowCount());

        // Set transaction value

        // Previously thougt use method in view to set a transaction
        // JFormattedTextField amount = new JFormattedTextField("50.0");
        // JFormattedTextField category = new JFormattedTextField("Food");
        // view.setAmountField(amount);
        // view.setCategoryField(category);

        // // Test if input is correct.
        // assertEquals(50.0, view.getAmountField(), 0.01);
        // assertEquals("Food", view.getCategoryField());

        double amount = 50.0;
        String category = "food";

        // Add transaction
        assertTrue(controller.addTransaction(amount, category));

        // Test if added
        // First line is transaction, second line is total
        assertEquals(2, view.getTransactionsTable().getRowCount());

        // Test if correctly added
        assertEquals(50.0, view.getTableModel().getValueAt(0, 1));
        assertEquals("food", view.getTableModel().getValueAt(0, 2));

        // Test if total is correct reflected
        assertEquals(50.0, view.getTableModel().getValueAt(1, 3));

    }

    @Test
    public void testInvalidInput() {

        assertEquals(0, model.getTransactions().size());

        double amount1 = -30.0;
        String category1 = "food";

        try {
            Transaction addedTransaction1 = new Transaction(amount1, category1);
            model.addTransaction(addedTransaction1);
        } catch (Exception e) {
            String errorMessage = e.getMessage();
            assertEquals("Bad assertion.", "The amount is not valid.", errorMessage);
        }

        double amount2 = 50.0;
        String category2 = "love";
        try {
            Transaction addedTransaction2 = new Transaction(amount2, category2);
            model.addTransaction(addedTransaction2);
        } catch (Exception e) {
            String errorMessage = e.getMessage();
            assertEquals("Bad Assertion", "The category is not valid.", errorMessage);
        }
    }

    @Test
    public void testAmountFilter() {

        assertEquals(0, model.getTransactions().size());

        double amount1 = 30.0;
        String category1 = "food";
        double amount2 = 30.0;
        String category2 = "other";
        double amount3 = 10.0;
        String category3 = "food";
        double amount4 = 45.0;
        String category4 = "bills";

        Transaction addedTransaction1 = new Transaction(amount1, category1);
        model.addTransaction(addedTransaction1);
        Transaction addedTransaction2 = new Transaction(amount2, category2);
        model.addTransaction(addedTransaction2);
        Transaction addedTransaction3 = new Transaction(amount3, category3);
        model.addTransaction(addedTransaction3);
        Transaction addedTransaction4 = new Transaction(amount4, category4);
        model.addTransaction(addedTransaction4);

        List<Transaction> transactions = model.getTransactions();
        AmountFilter amf = new AmountFilter(30.0);
        List<Transaction> amfTransactions = amf.filter(transactions);
        assertEquals("Not equal to 2.", amfTransactions.size(), 2);
    }

    @Test
    public void testCategoryFilter() {
        assertEquals(0, model.getTransactions().size());

        double amount1 = 30.0;
        String category1 = "food";
        double amount2 = 30.0;
        String category2 = "other";
        double amount3 = 10.0;
        String category3 = "food";
        double amount4 = 45.0;
        String category4 = "bills";

        Transaction addedTransaction1 = new Transaction(amount1, category1);
        model.addTransaction(addedTransaction1);
        Transaction addedTransaction2 = new Transaction(amount2, category2);
        model.addTransaction(addedTransaction2);
        Transaction addedTransaction3 = new Transaction(amount3, category3);
        model.addTransaction(addedTransaction3);
        Transaction addedTransaction4 = new Transaction(amount4, category4);
        model.addTransaction(addedTransaction4);

        List<Transaction> transactions = model.getTransactions();
        CategoryFilter cf = new CategoryFilter("food");
        List<Transaction> cfTransactions = cf.filter(transactions);
        assertEquals("Not Equal to 2.", cfTransactions.size(), 2);
    }

    // @Test
    // public void testUndoAllowed() {
    //     double amount1 = 30.0;
    //     String category1 = "food";
    //     double amount2 = 30.0;
    //     String category2 = "other";
    //     double amount3 = 10.0;
    //     String category3 = "food";
    //     double amount4 = 45.0;
    //     String category4 = "bills";

    //     controller.addTransaction(amount1, category1);
    //     controller.addTransaction(amount2, category2);
    //     controller.addTransaction(amount3, category3);
    //     controller.addTransaction(amount4, category4);

    //     assertEquals(5, view.getTransactionsTable().getRowCount());

    //     controller.undoTransaction(view.getTransactionsTable().getSelectedRows());
    //     assertEquals(2, view.getTransactionsTable().getSelectedRows());

    // }

    
    @Test
    public void testUndoDisallowed() {


        // Error message
        String emptyMessage = "This undo is not allowed: The transaction list is empty.";
        String notSelectMessage = "This undo is not allowed: No transaction is selected.";

        double amount = 50.0;
        String category = "food";

        JTable transactionsTable = view.getTransactionsTable();
        int[] selectedRows = transactionsTable.getSelectedRows();

        try {
            controller.undoTransaction(selectedRows);
        } catch (Exception e) {
            String errorMessage = e.getMessage();
            assertEquals(emptyMessage, errorMessage);
        }

        assertTrue(controller.addTransaction(amount, category));

        try {
            controller.undoTransaction(selectedRows);
        } catch (Exception e) {
            String errorMessage = e.getMessage();
            assertEquals(notSelectMessage, errorMessage);
        }

        transactionsTable.addRowSelectionInterval(0, 0);
        selectedRows = transactionsTable.getSelectedRows();

        try {
            controller.undoTransaction(selectedRows);
        } catch (Exception e) {
        }

        assertEquals(0, model.getTransactions().size());
    }

}

