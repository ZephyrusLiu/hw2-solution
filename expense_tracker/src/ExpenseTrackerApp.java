import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

// import controller.DateFilter;
import controller.CategoryFilter;
import controller.ExpenseTrackerController;
import model.ExpenseTrackerModel;
import view.ExpenseTrackerView;
import model.Transaction;

public class ExpenseTrackerApp {

  public static void main(String[] args) {
    
    // Create MVC components
    ExpenseTrackerModel model = new ExpenseTrackerModel();
    DefaultTableModel tableModel = new DefaultTableModel();
    tableModel.addColumn("Amount");
    tableModel.addColumn("Category");
    tableModel.addColumn("Date");
    ExpenseTrackerView view = new ExpenseTrackerView(tableModel);
    ExpenseTrackerController controller = new ExpenseTrackerController(model, view);
    

    // Initialize view
    view.setVisible(true);

    // Add action listener to the "Apply Category Filter" button
    view.addApplyCategoryFilterListener(e -> {
      String categoryFilterInput = view.getCategoryFilterInput();
      if (categoryFilterInput != null) {
          controller.applyCategoryFilter(categoryFilterInput);
      }
    });

    // Handle add transaction button clicks
    view.getAddTransactionBtn().addActionListener(e -> {
      // Get transaction data from view
      double amount = view.getAmountField();
      String category = view.getCategoryField();
      
      // Call controller to add transaction
      boolean added = controller.addTransaction(amount, category);
      
      if (!added) {
        JOptionPane.showMessageDialog(view, "Invalid amount or category entered");
        view.toFront();
      }
    });

  }
}