package com.example.quanlychitieu.services;

import com.example.quanlychitieu.models.Expense;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Label;  // Correct import for JavaFX Label

import java.util.List;

public class ExpenseService {
    private ObservableList<Expense> expenses;
    public ExpenseService() {
        expenses = FXCollections.observableArrayList();
    }
}
