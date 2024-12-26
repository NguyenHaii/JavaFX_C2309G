package com.example.quanlychitieu.ui;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import com.example.quanlychitieu.models.BinaryTree;
import com.example.quanlychitieu.models.Expense;
import com.example.quanlychitieu.services.ExpenseService;

import java.time.LocalDate;

public class MainLayoutController {
    @FXML
    private TableView<Expense> expenseTable;
    @FXML
    private TableView<Expense> incomeTable;
    @FXML
    private TextField amountField;
    @FXML
    private ComboBox<String> categoryComboBox;
    @FXML
    private DatePicker datePicker;
    @FXML
    private ComboBox<Expense.Type> typeComboBox;
    @FXML
    private Label totalLabel;
    @FXML
    private Label revenueLabel;
    @FXML
    private Button calculateRevenueButton;

    private BinaryTree<Expense> expenseTree;
    private BinaryTree<Expense> incomeTree;

    public void initialize() {
        expenseTree = new BinaryTree<>();
        incomeTree = new BinaryTree<>();

        // Set options for typeComboBox
        typeComboBox.getItems().setAll(Expense.Type.CHI, Expense.Type.THU);
        typeComboBox.setOnAction(event -> updateCategoryOptions());

        setupTableColumns(expenseTable);
        setupTableColumns(incomeTable);

        setupTable(expenseTable, Expense.Type.CHI);
        setupTable(incomeTable, Expense.Type.THU);

        updateTotalLabel();
    }

    private void setupTableColumns(TableView<Expense> table) {
        TableColumn<Expense, String> categoryColumn = new TableColumn<>("Danh Mục");
        categoryColumn.setCellValueFactory(cellData -> cellData.getValue().categoryProperty());

        TableColumn<Expense, Double> amountColumn = new TableColumn<>("Số Tiền");
        amountColumn.setCellValueFactory(cellData -> cellData.getValue().amountProperty());

        TableColumn<Expense, LocalDate> dateColumn = new TableColumn<>("Ngày");
        dateColumn.setCellValueFactory(cellData -> cellData.getValue().dateProperty());

        table.getColumns().addAll(categoryColumn, amountColumn, dateColumn);
    }

    private void setupTable(TableView<Expense> table, Expense.Type type) {
        ObservableList<Expense> filteredExpenses = FXCollections.observableArrayList();

        if (type == Expense.Type.CHI) {
            filteredExpenses.addAll(expenseTree.inOrderTraversal());
        } else {
            filteredExpenses.addAll(incomeTree.inOrderTraversal());
        }

        table.setItems(filteredExpenses);
    }

    private void updateCategoryOptions() {
        Expense.Type selectedType = typeComboBox.getValue();
        categoryComboBox.getItems().clear();

        if (selectedType == Expense.Type.CHI) {
            categoryComboBox.getItems().addAll("Ăn uống", "Giải trí", "Hóa đơn", "Khác");
        } else if (selectedType == Expense.Type.THU) {
            categoryComboBox.getItems().addAll("Bố mẹ cho", "Tiền làm thêm", "Tiền ăn cắp", "Khác");
        }
    }

    public void addExpense() {
        Expense.Type selectedType = typeComboBox.getValue();
        if (selectedType != Expense.Type.CHI) {
            showError("Chọn loại Chi trước khi thêm.");
            return;
        }
        addRecord(selectedType);
    }

    public void addIncome() {
        Expense.Type selectedType = typeComboBox.getValue();
        if (selectedType != Expense.Type.THU) {
            showError("Chọn loại Thu trước khi thêm.");
            return;
        }
        addRecord(selectedType);
    }

    private void addRecord(Expense.Type type) {
        try {
            double amount = Double.parseDouble(amountField.getText());
            String category = categoryComboBox.getValue();
            LocalDate date = datePicker.getValue();

            if (category == null || date == null) {
                throw new IllegalArgumentException("Vui lòng điền đầy đủ thông tin.");
            }

            Expense expense = new Expense(amount, category, date, type);

            if (type == Expense.Type.CHI) {
                expenseTree.add(expense);
            } else {
                incomeTree.add(expense);
            }

            updateTotalLabel();
            setupTable(expenseTable, Expense.Type.CHI);
            setupTable(incomeTable, Expense.Type.THU);

            amountField.clear();
            categoryComboBox.setValue(null);
            datePicker.setValue(null);
            typeComboBox.setValue(null);
        } catch (Exception e) {
            showError("Vui lòng kiểm tra lại số tiền, danh mục và ngày!");
        }
    }

    private void showError(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Lỗi nhập liệu");
        alert.setHeaderText("Thông tin không hợp lệ");
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void updateTotalLabel() {
        double totalExpense = expenseTree.inOrderTraversal().stream().mapToDouble(Expense::getAmount).sum();
        double totalIncome = incomeTree.inOrderTraversal().stream().mapToDouble(Expense::getAmount).sum();
        totalLabel.setText("Tổng Chi: " + totalExpense + " | Tổng Thu: " + totalIncome);
    }

    @FXML
    private void calculateRevenue() {
        double totalExpense = expenseTree.inOrderTraversal().stream().mapToDouble(Expense::getAmount).sum();
        double totalIncome = incomeTree.inOrderTraversal().stream().mapToDouble(Expense::getAmount).sum();
        double revenue = totalIncome - totalExpense;

        revenueLabel.setText("Tổng Doanh Thu: " + (revenue < 0 ? revenue : "+" + revenue));
    }

    // Actions for menu buttons
    @FXML
    private void showList() {
        // Show list of expenses and incomes
        setupTable(expenseTable, Expense.Type.CHI);
        setupTable(incomeTable, Expense.Type.THU);
    }

    @FXML
    private void showAdd() {
        // Show add expense and income form (right panel with input fields)
        // You may want to clear or focus the form elements here
    }

    @FXML
    private void showRevenue() {
        // Show revenue calculation (triggering revenue calculation)
        calculateRevenue();
    }
}
