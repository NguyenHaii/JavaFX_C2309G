package com.example.demo;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Book {

    private final StringProperty name;
    private final StringProperty author;
    private final StringProperty price;
    private final StringProperty publishDate;

    public Book(String name, String author, String price, String publishDate) {
        this.name = new SimpleStringProperty(name);
        this.author = new SimpleStringProperty(author);
        this.price = new SimpleStringProperty(price);
        this.publishDate = new SimpleStringProperty(publishDate);
    }

    public StringProperty nameProperty() {
        return name;
    }

    public StringProperty authorProperty() {
        return author;
    }

    public StringProperty priceProperty() {
        return price;
    }

    public StringProperty publishDateProperty() {
        return publishDate;
    }
}
