package com.example.quanlychitieu.services;

import com.example.quanlychitieu.models.Expense;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Label;  // Correct import for JavaFX Label

import java.util.List;

public class ExpenseService {
    private ObservableList<Expense> expenses;
    private double totalExpenses;
    private double totalIncome;

    public ExpenseService() {
        expenses = FXCollections.observableArrayList();
        totalExpenses = 0;
        totalIncome = 0;
    }

    public void addExpense(Expense expense) {
        expenses.add(expense);

        // Update totals based on type of expense
        if (expense.getType() == Expense.Type.CHI) {
            totalExpenses += expense.getAmount();
        } else if (expense.getType() == Expense.Type.THU) {
            totalIncome += expense.getAmount();
        }
    }

    public List<Expense> getSortedExpenses() {
        expenses.sort(Expense::compareTo);
        return expenses;
    }

    public void setTotalLabel(Label totalLabel) {
        totalLabel.setText("Tổng chi: " + totalExpenses + " VNĐ | Tổng thu: " + totalIncome + " VNĐ");
    }
}
