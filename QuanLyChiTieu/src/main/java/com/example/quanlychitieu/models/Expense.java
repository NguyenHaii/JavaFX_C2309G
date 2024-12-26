package com.example.quanlychitieu.models;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleDoubleProperty;

import java.time.LocalDate;

public class Expense implements Comparable<Expense> {
    private ObjectProperty<Double> amount;  // Use DoubleProperty for amount
    private ObjectProperty<String> category;
    private ObjectProperty<LocalDate> date;
    private ObjectProperty<Type> type;  // Enum to distinguish expense or income

    public enum Type {
        CHI, THU
    }

    public Expense(double amount, String category, LocalDate date, Type type) {
        this.amount = new SimpleDoubleProperty(amount).asObject();  // Initialize amount as SimpleDoubleProperty
        this.category = new SimpleObjectProperty<>(category);
        this.date = new SimpleObjectProperty<>(date);
        this.type = new SimpleObjectProperty<>(type);
    }

    public ObjectProperty<Double> amountProperty() {
        return amount;  // Return the SimpleDoubleProperty for amount
    }

    public ObjectProperty<String> categoryProperty() {
        return category;  // Return the SimpleObjectProperty for category
    }

    public ObjectProperty<LocalDate> dateProperty() {
        return date;  // Return the SimpleObjectProperty for date
    }

    public ObjectProperty<Type> typeProperty() {
        return type;  // Return the SimpleObjectProperty for type
    }

    public double getAmount() {
        return amount.get();  // Get the value from the SimpleDoubleProperty
    }

    public String getCategory() {
        return category.get();  // Get the value from the SimpleObjectProperty
    }

    public LocalDate getDate() {
        return date.get();  // Get the value from the SimpleObjectProperty
    }

    public Type getType() {
        return type.get();  // Get the value from the SimpleObjectProperty
    }

    @Override
    public int compareTo(Expense other) {
        return this.date.get().compareTo(other.date.get());
    }
}
