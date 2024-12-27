package com.example.quanlychitieu.ui;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.chart.PieChart;
import javafx.scene.control.*;
import com.example.quanlychitieu.models.BinaryTree;
import com.example.quanlychitieu.models.Expense;
import com.example.quanlychitieu.services.ExpenseService;

import java.time.LocalDate;
import java.util.stream.Collectors;

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
    @FXML
    private PieChart expenseChart;  // Đổi BarChart thành PieChart
    @FXML
    private PieChart incomeChart;   // Đổi BarChart thành PieChart
    @FXML
    private TabPane tabPane;

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
            filteredExpenses.setAll(expenseTree.inOrderTraversal());
        } else {
            filteredExpenses.setAll(incomeTree.inOrderTraversal());
        }

        table.setItems(filteredExpenses);  // Cập nhật bảng với dữ liệu mới
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
        addRecord(Expense.Type.CHI);
    }

    public void addIncome() {
        Expense.Type selectedType = typeComboBox.getValue();
        if (selectedType != Expense.Type.THU) {
            showError("Chọn loại Thu trước khi thêm.");
            return;
        }
        addRecord(Expense.Type.THU);
    }

    private void addRecord(Expense.Type type) {
        try {
            double amount = Double.parseDouble(amountField.getText());
            String category = categoryComboBox.getValue();
            LocalDate date = datePicker.getValue();

            if (category == null || date == null) {
                throw new IllegalArgumentException("Vui lòng điền đầy đủ thông tin.");
            }

            // Check if expense or income is already in the tree (to avoid duplicates)
            Expense expense = new Expense(amount, category, date, type);

            if (type == Expense.Type.CHI) {
                if (!expenseTree.contains(expense)) {
                    expenseTree.add(expense);  // Add expense only if not already in the tree
                } else {
                    showError("Chi tiêu này đã tồn tại.");
                    return;
                }
            } else {
                if (!incomeTree.contains(expense)) {
                    incomeTree.add(expense);  // Add income only if not already in the tree
                } else {
                    showError("Thu nhập này đã tồn tại.");
                    return;
                }
            }

            // Cập nhật bảng và biểu đồ sau khi thêm dữ liệu
            updateTableAndCharts();  // Đảm bảo bảng và biểu đồ được cập nhật

            // Xóa dữ liệu sau khi thêm thành công
            amountField.clear();
            categoryComboBox.setValue(null);
            datePicker.setValue(null);
            typeComboBox.setValue(null);

        } catch (Exception e) {
            showError("Vui lòng kiểm tra lại số tiền, danh mục và ngày!");
        }
    }





    private void updateTableAndCharts() {
        // Cập nhật bảng
        setupTable(expenseTable, Expense.Type.CHI);
        setupTable(incomeTable, Expense.Type.THU);

        // Cập nhật biểu đồ
        updateExpenseChart();
        updateIncomeChart();
        updateTotalLabel();
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

    private void updateExpenseChart() {
        // Cập nhật biểu đồ chi tiêu
        expenseChart.getData().clear();
        ObservableList<PieChart.Data> expenseData = FXCollections.observableArrayList();

        double totalExpense = expenseTree.inOrderTraversal().stream()
                .mapToDouble(Expense::getAmount)
                .sum();

        expenseTree.inOrderTraversal().stream()
                .collect(Collectors.groupingBy(Expense::getCategory, Collectors.summingDouble(Expense::getAmount)))
                .forEach((category, totalAmount) -> {
                    double percentage = (totalAmount / totalExpense) * 100;
                    PieChart.Data data = new PieChart.Data(category + " (" + String.format("%.1f", percentage) + "%)", totalAmount);
                    expenseData.add(data);
                });

        expenseChart.setData(expenseData);
    }

    private void updateIncomeChart() {
        // Cập nhật biểu đồ thu nhập
        incomeChart.getData().clear();
        ObservableList<PieChart.Data> incomeData = FXCollections.observableArrayList();

        double totalIncome = incomeTree.inOrderTraversal().stream()
                .mapToDouble(Expense::getAmount)
                .sum();

        incomeTree.inOrderTraversal().stream()
                .collect(Collectors.groupingBy(Expense::getCategory, Collectors.summingDouble(Expense::getAmount)))
                .forEach((category, totalAmount) -> {
                    double percentage = (totalAmount / totalIncome) * 100;
                    PieChart.Data data = new PieChart.Data(category + " (" + String.format("%.1f", percentage) + "%)", totalAmount);
                    incomeData.add(data);
                });

        incomeChart.setData(incomeData);
    }
    @FXML
    private void calculateRevenue() {
        double totalExpense = expenseTree.inOrderTraversal().stream().mapToDouble(Expense::getAmount).sum();
        double totalIncome = incomeTree.inOrderTraversal().stream().mapToDouble(Expense::getAmount).sum();
        double revenue = totalIncome - totalExpense;

        revenueLabel.setText("Tổng Doanh Thu: " + (revenue < 0 ? revenue : "+" + revenue));
    }

}
