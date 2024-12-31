package com.example.quanlychitieu.ui;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.chart.PieChart;
import com.example.quanlychitieu.models.BinaryTree;
import com.example.quanlychitieu.models.Expense;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Alert;


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
    private PieChart expenseChart;
    @FXML
    private PieChart incomeChart;
    @FXML
    private ComboBox<String> filterCategoryComboBox;
    @FXML
    private ComboBox<Expense.Type> filterTypeComboBox;
    @FXML
    private TextField searchMinAmountField;
    @FXML
    private TextField searchMaxAmountField;
    @FXML
    private DatePicker searchDatePicker;
    @FXML
    private TableView<Expense> filterTable; // Table for filtered results
    @FXML
    private TableView<Expense> searchTable; // Table for search results
    @FXML
    private TabPane tabPane;
    @FXML
    private Tab danhSachTab, themTab, doanhThuTab, bieuDoTab, locTab, timKiemTab;


    @FXML
    private void showDanhSachTab() {
        if (!tabPane.getTabs().contains(danhSachTab)) {
            tabPane.getTabs().add(danhSachTab);  // Re-add the tab if it was closed
        }
        tabPane.getSelectionModel().select(danhSachTab); // Switch to the tab
    }

    @FXML
    private void showThemTab() {
        if (!tabPane.getTabs().contains(themTab)) {
            tabPane.getTabs().add(themTab);  // Re-add the tab if it was closed
        }
        tabPane.getSelectionModel().select(themTab); // Switch to the tab
    }

    @FXML
    private void showDoanhThuTab() {
        if (!tabPane.getTabs().contains(doanhThuTab)) {
            tabPane.getTabs().add(doanhThuTab);  // Re-add the tab if it was closed
        }
        tabPane.getSelectionModel().select(doanhThuTab); // Switch to the tab
    }

    @FXML
    private void showBieuDoTab() {
        if (!tabPane.getTabs().contains(bieuDoTab)) {
            tabPane.getTabs().add(bieuDoTab);  // Re-add the tab if it was closed
        }
        tabPane.getSelectionModel().select(bieuDoTab); // Switch to the tab
    }

    @FXML
    private void showLocTab() {
        if (!tabPane.getTabs().contains(locTab)) {
            tabPane.getTabs().add(locTab);  // Re-add the tab if it was closed
        }
        tabPane.getSelectionModel().select(locTab); // Switch to the tab
    }

    @FXML
    private void showTimKiemTab() {
        if (!tabPane.getTabs().contains(timKiemTab)) {
            tabPane.getTabs().add(timKiemTab);  // Re-add the tab if it was closed
        }
        tabPane.getSelectionModel().select(timKiemTab); // Switch to the tab
    }



    private BinaryTree<Expense> expenseTree;
    private BinaryTree<Expense> incomeTree;

    public void initialize() {
        expenseTree = new BinaryTree<>();
        incomeTree = new BinaryTree<>();

        typeComboBox.getItems().setAll(Expense.Type.CHI, Expense.Type.THU);
        typeComboBox.setOnAction(event -> updateCategoryOptions());

        setupTableColumns(expenseTable);
        setupTableColumns(incomeTable);
        setupTableColumns(searchTable);

        setupTable(expenseTable, Expense.Type.CHI);
        setupTable(incomeTable, Expense.Type.THU);

        filterTypeComboBox.getItems().setAll(Expense.Type.CHI, Expense.Type.THU);
        filterTypeComboBox.setOnAction(event -> updateFilterCategoryOptions());

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

    private void updateFilterCategoryOptions() {
        Expense.Type selectedType = filterTypeComboBox.getValue();
        filterCategoryComboBox.getItems().clear();

        if (selectedType == Expense.Type.CHI) {
            filterCategoryComboBox.getItems().addAll("Ăn uống", "Giải trí", "Hóa đơn", "Khác");
        } else if (selectedType == Expense.Type.THU) {
            filterCategoryComboBox.getItems().addAll("Bố mẹ cho", "Tiền làm thêm", "Tiền ăn cắp", "Khác");
        }
    }

    @FXML
    private void applyFilter() {
        Expense.Type selectedType = filterTypeComboBox.getValue();
        String selectedCategory = filterCategoryComboBox.getValue();

        if (selectedType == null || selectedCategory == null) {
            showError("Vui lòng chọn loại và danh mục.");
            return;
        }

        ObservableList<Expense> filteredResults = FXCollections.observableArrayList();

        if (selectedType == Expense.Type.CHI) {
            List<Expense> filteredExpenses = expenseTree.filterByCategory(selectedType, selectedCategory);
            filteredResults.setAll(filteredExpenses);
        } else if (selectedType == Expense.Type.THU) {
            List<Expense> filteredIncomes = incomeTree.filterByCategory(selectedType, selectedCategory);
            filteredResults.setAll(filteredIncomes);
        }

        if (filteredResults.isEmpty()) {
            showError("Không tìm thấy dữ liệu khớp với tiêu chí lọc.");
        } else {
            filterTable.setItems(filteredResults);
            showSuccess("Lọc thành công!");
        }

        updateExpenseChart();
        updateIncomeChart();
    }

    @FXML
    private void searchRecords() {
        try {
            double minAmount = Double.parseDouble(searchMinAmountField.getText());
            double maxAmount = Double.parseDouble(searchMaxAmountField.getText());
            LocalDate selectedMonth = searchDatePicker.getValue();

            ObservableList<Expense> searchResults = FXCollections.observableArrayList();

            searchResults.setAll(expenseTree.inOrderTraversal().stream()
                    .filter(expense -> expense.getAmount() >= minAmount && expense.getAmount() <= maxAmount &&
                            (selectedMonth == null || expense.getDate().getMonth().equals(selectedMonth.getMonth())))
                    .collect(Collectors.toList()));

            searchResults.addAll(incomeTree.inOrderTraversal().stream()
                    .filter(expense -> expense.getAmount() >= minAmount && expense.getAmount() <= maxAmount &&
                            (selectedMonth == null || expense.getDate().getMonth().equals(selectedMonth.getMonth())))
                    .collect(Collectors.toList()));

            if (searchResults.isEmpty()) {
                showError("Không tìm thấy kết quả phù hợp.");
            } else {
                searchTable.setItems(searchResults);
                showSuccess("Tìm kiếm thành công!");
            }
        } catch (NumberFormatException e) {
            showError("Vui lòng nhập đúng định dạng số cho khoảng giá tiền.");
        }
    }


    @FXML
    private void addExpense() {
        try {
            if (typeComboBox.getValue() != Expense.Type.CHI) {
                showError("Vui lòng chọn loại chi.");
                return;
            }

            double amount = Double.parseDouble(amountField.getText());
            String category = categoryComboBox.getValue();
            LocalDate date = datePicker.getValue();

            if (amount <= 0 || category == null || date == null) {
                showError("Vui lòng nhập đầy đủ thông tin hợp lệ.");
                return;
            }

            Expense expense = new Expense(amount, category, date, Expense.Type.CHI);
            expenseTree.insert(expense); // Insert the new expense into the tree
            setupTable(expenseTable, Expense.Type.CHI); // Update the table with the new data
            updateExpenseChart();  // Update the chart after adding a new expense
            updateTotalLabel(); // Update the total label to reflect new totals

            // Clear input fields
            amountField.clear();
            categoryComboBox.setValue(null);
            datePicker.setValue(null);

            // Show success message
            showSuccess("Chi tiêu đã được thêm thành công!");
        } catch (NumberFormatException e) {
            showError("Vui lòng nhập số hợp lệ cho số tiền.");
        }
    }



    @FXML
    private void addIncome() {
        try {
            if (typeComboBox.getValue() != Expense.Type.THU) {
                showError("Vui lòng chọn loại thu.");
                return;
            }

            double amount = Double.parseDouble(amountField.getText());
            String category = categoryComboBox.getValue();
            LocalDate date = datePicker.getValue();

            if (amount <= 0 || category == null || date == null) {
                showError("Vui lòng nhập đầy đủ thông tin hợp lệ.");
                return;
            }

            Expense income = new Expense(amount, category, date, Expense.Type.THU);
            incomeTree.insert(income); // Insert the new income into the tree
            setupTable(incomeTable, Expense.Type.THU); // Update the table with the new data
            updateIncomeChart();  // Update the chart after adding a new income
            updateTotalLabel(); // Update the total label to reflect new totals

            // Clear input fields
            amountField.clear();
            categoryComboBox.setValue(null);
            datePicker.setValue(null);

            // Show success message
            showSuccess("Thu nhập đã được thêm thành công!");
        } catch (NumberFormatException e) {
            showError("Vui lòng nhập số hợp lệ cho số tiền.");
        }
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

        revenueLabel.setText("Tổng Doanh Thu: " + revenue);

        updateExpenseChart();
        updateIncomeChart();
        updateTotalLabel();

        showSuccess("Tính toán doanh thu thành công!");
    }



    private void updateTotalLabel() {
        double totalExpense = expenseTree.inOrderTraversal().stream().mapToDouble(Expense::getAmount).sum();
        double totalIncome = incomeTree.inOrderTraversal().stream().mapToDouble(Expense::getAmount).sum();
        totalLabel.setText("Tổng Chi: " + totalExpense + " | Tổng Thu: " + totalIncome);
    }

    private void showError(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Lỗi");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void showSuccess(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Thành công");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }


}
