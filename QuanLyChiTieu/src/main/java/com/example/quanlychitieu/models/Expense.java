package com.example.quanlychitieu.models;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleDoubleProperty;

import java.time.LocalDate;
import java.util.Objects;

public class Expense implements Comparable<Expense> {
    private ObjectProperty<Double> amount;  // Use ObjectProperty for amount
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
        // Compare by type first, then category, then amount, and finally date
        int typeComparison = this.getType().compareTo(other.getType());
        if (typeComparison != 0) return typeComparison;

        int categoryComparison = this.getCategory().compareTo(other.getCategory());
        if (categoryComparison != 0) return categoryComparison;

        int amountComparison = Double.compare(this.getAmount(), other.getAmount());
        if (amountComparison != 0) return amountComparison;

        return this.getDate().compareTo(other.getDate());  // Compare by date if all other fields are equal
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Expense expense = (Expense) obj;
        return Double.compare(expense.getAmount(), getAmount()) == 0 &&
                Objects.equals(getCategory(), expense.getCategory()) &&
                Objects.equals(getDate(), expense.getDate()) &&
                getType() == expense.getType();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getAmount(), getCategory(), getDate(), getType());
    }
}
