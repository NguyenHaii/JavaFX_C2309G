package com.example.quanlychitieu.ui;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import com.example.quanlychitieu.services.ExpenseService;
import com.example.quanlychitieu.models.Expense;

import java.time.LocalDate;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MainLayout {
    private BorderPane root;
    private ExpenseService expenseService;
    private Label totalLabel;

    public MainLayout() {
        root = new BorderPane();
        expenseService = new ExpenseService();

        // Apply CSS Stylesheet
        root.getStylesheets().add(getClass().getResource("style.css").toExternalForm());

        // Tiêu đề
        Label titleLabel = new Label("Quản Lý Chi Tiêu");
        titleLabel.setId("titleLabel");  // Assign ID for specific CSS styling
        root.setTop(titleLabel);
        BorderPane.setAlignment(titleLabel, javafx.geometry.Pos.CENTER);

        // Create two TableViews: one for expenses and one for income
        TableView<Expense> expenseTable = createTableView(Expense.Type.CHI);
        TableView<Expense> incomeTable = createTableView(Expense.Type.THU);

        // Create VBox for "Expense" table
        VBox expenseBox = new VBox(5);
        Label expenseTitle = new Label("Expense");
        expenseTitle.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");
        expenseBox.getChildren().addAll(expenseTitle, expenseTable);

        // Create VBox for "Income" table
        VBox incomeBox = new VBox(5);
        Label incomeTitle = new Label("Income");
        incomeTitle.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");
        incomeBox.getChildren().addAll(incomeTitle, incomeTable);

        // Layout for the tables
        HBox tablesContainer = new HBox(20); // Add space between the tables
        tablesContainer.getChildren().addAll(expenseBox, incomeBox);
        root.setCenter(tablesContainer);

        // Form to add a new expense/income
        VBox form = new VBox(10);
        form.setStyle("-fx-padding: 10;");
        TextField amountField = new TextField();
        amountField.setPromptText("Nhập số tiền...");
        ComboBox<String> categoryComboBox = new ComboBox<>();
        categoryComboBox.getItems().addAll("Ăn uống", "Giải trí", "Hóa đơn", "Khác");
        categoryComboBox.setPromptText("Chọn danh mục");
        DatePicker datePicker = new DatePicker();
        ComboBox<Expense.Type> typeComboBox = new ComboBox<>();
        typeComboBox.getItems().setAll(Expense.Type.CHI, Expense.Type.THU);
        typeComboBox.setPromptText("Chọn loại");

        Button addButton = new Button("Thêm");

        addButton.setOnAction(e -> {
            try {
                double amount = Double.parseDouble(amountField.getText());
                String category = categoryComboBox.getValue();
                LocalDate date = datePicker.getValue();
                Expense.Type type = typeComboBox.getValue();

                if (category == null || date == null || type == null) {
                    throw new IllegalArgumentException("Vui lòng điền đầy đủ thông tin.");
                }

                Expense expense = new Expense(amount, category, date, type);
                expenseService.addExpense(expense);

                // Update both tables with the sorted expenses
                updateTableView(expenseTable, Expense.Type.CHI);
                updateTableView(incomeTable, Expense.Type.THU);

                // Update the total label
                expenseService.setTotalLabel(totalLabel);

                amountField.clear();
                categoryComboBox.setValue(null);
                datePicker.setValue(null);
                typeComboBox.setValue(null);
            } catch (Exception ex) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Lỗi nhập liệu");
                alert.setHeaderText("Thông tin không hợp lệ");
                alert.setContentText("Vui lòng kiểm tra lại số tiền, danh mục và ngày!");
                alert.showAndWait();
            }
        });

        form.getChildren().addAll(
                new Label("Số tiền:"), amountField,
                new Label("Danh mục:"), categoryComboBox,
                new Label("Ngày:"), datePicker,
                new Label("Loại:"), typeComboBox,
                addButton
        );

        root.setRight(form);

        // Display total amount (both expenses and income)
        totalLabel = new Label("Tổng chi: 0 VNĐ | Tổng thu: 0 VNĐ");
        totalLabel.setStyle("-fx-padding: 10; -fx-font-size: 16px;");
        root.setBottom(totalLabel);

        // Initialize totals
        expenseService.setTotalLabel(totalLabel);
    }


    private TableView<Expense> createTableView(Expense.Type type) {
        TableView<Expense> tableView = new TableView<>();

        // Category Column
        TableColumn<Expense, String> categoryColumn = new TableColumn<>("Danh Mục");
        categoryColumn.setCellValueFactory(cellData -> cellData.getValue().categoryProperty());

        // Amount Column
        TableColumn<Expense, Double> amountColumn = new TableColumn<>("Số Tiền");
        amountColumn.setCellValueFactory(cellData -> cellData.getValue().amountProperty());

        // Date Column
        TableColumn<Expense, LocalDate> dateColumn = new TableColumn<>("Ngày");
        dateColumn.setCellValueFactory(cellData -> cellData.getValue().dateProperty());

        tableView.getColumns().addAll(categoryColumn, amountColumn, dateColumn);

        // Row factory to apply different styles based on type
        tableView.setRowFactory(tv -> {
            TableRow<Expense> row = new TableRow<>();
            row.setStyle("-fx-background-color: #ffffff;");

            row.itemProperty().addListener((obs, oldItem, newItem) -> {
                if (newItem != null) {
                    if (newItem.getType() == Expense.Type.CHI) {
                        row.setStyle("-fx-background-color: #ffcccc;");
                    } else if (newItem.getType() == Expense.Type.THU) {
                        row.setStyle("-fx-background-color: #cce5ff;");
                    }
                }
            });

            return row;
        });

        // Filter the table based on the type
        ObservableList<Expense> filteredExpenses = FXCollections.observableArrayList(expenseService.getSortedExpenses());
        filteredExpenses.removeIf(expense -> expense.getType() != type);
        tableView.setItems(filteredExpenses);

        return tableView;
    }

    private void updateTableView(TableView<Expense> tableView, Expense.Type type) {
        ObservableList<Expense> filteredExpenses = FXCollections.observableArrayList(expenseService.getSortedExpenses());
        filteredExpenses.removeIf(expense -> expense.getType() != type);
        tableView.setItems(filteredExpenses);
    }

    public BorderPane getRoot() {
        return root;
    }
}
