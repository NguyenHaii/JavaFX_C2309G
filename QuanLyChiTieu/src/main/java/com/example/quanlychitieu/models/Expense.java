package com.example.quanlychitieu.models;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;

import java.time.LocalDate;
import java.util.Objects;

public class Expense implements Comparable<Expense> {
    private final ObjectProperty<Double> amount;
    private final ObjectProperty<String> category;
    private final ObjectProperty<LocalDate> date;
    private final ObjectProperty<Type> type;
    private double percentage;

    public enum Type {
        CHI, THU
    }

    public Expense(double amount, String category, LocalDate date, Type type) {
        this.amount = new SimpleObjectProperty<>(amount);
        this.category = new SimpleObjectProperty<>(category);
        this.date = new SimpleObjectProperty<>(date);
        this.type = new SimpleObjectProperty<>(type);
    }

    // Properties for binding
    public ObjectProperty<Double> amountProperty() {
        return amount;
    }

    public ObjectProperty<String> categoryProperty() {
        return category;
    }

    public ObjectProperty<LocalDate> dateProperty() {
        return date;
    }

    public ObjectProperty<Type> typeProperty() {
        return type;
    }


    // Getters
    public Double getAmount() {
        return amount.get();
    }

    public String getCategory() {
        return category.get();
    }

    public LocalDate getDate() {
        return date.get();
    }

    public Type getType() {
        return type.get();
    }
    public double getPercentage() {
        return percentage;
    }

    // Setters
    public void setAmount(Double amount) {
        this.amount.set(amount);
    }

    public void setCategory(String category) {
        this.category.set(category);
    }

    public void setDate(LocalDate date) {
        this.date.set(date);
    }

    public void setType(Type type) {
        this.type.set(type);
    }


    public void setPercentage(double percentage) {
        this.percentage = percentage;
    }

    @Override
    public int compareTo(Expense other) {
        int typeComparison = this.getType().compareTo(other.getType());
        if (typeComparison != 0) return typeComparison;

        int categoryComparison = this.getCategory().compareTo(other.getCategory());
        if (categoryComparison != 0) return categoryComparison;

        int amountComparison = Double.compare(this.getAmount(), other.getAmount());
        if (amountComparison != 0) return amountComparison;

        return this.getDate().compareTo(other.getDate());
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Expense expense = (Expense) obj;
        return Objects.equals(getAmount(), expense.getAmount()) &&
                Objects.equals(getCategory(), expense.getCategory()) &&
                Objects.equals(getDate(), expense.getDate()) &&
                getType() == expense.getType();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getAmount(), getCategory(), getDate(), getType());
    }
}
