package com.example.dbsa2;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class Student {
    private final SimpleStringProperty name;
    private final SimpleIntegerProperty score;

    public Student(String name, int score) {
        this.name = new SimpleStringProperty(name);
        this.score = new SimpleIntegerProperty(score);
    }

    public String getName() {
        return name.get();
    }

    public SimpleStringProperty nameProperty() {
        return name;
    }

    public int getScore() {
        return score.get();
    }

    public SimpleIntegerProperty scoreProperty() {
        return score;
    }
}
