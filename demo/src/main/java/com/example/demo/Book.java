package com.example.demo;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Book {
    private StringProperty name;
    private StringProperty author;
    private StringProperty price;
    private StringProperty publishDate;

    public Book(String name, String author, String price, String publishDate) {
        this.name = new SimpleStringProperty(name);
        this.author = new SimpleStringProperty(author);
        this.price = new SimpleStringProperty(price);
        this.publishDate = new SimpleStringProperty(publishDate);
    }

    // Getter and Setter for name
    public String getName() {
        return name.get();
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public StringProperty nameProperty() {
        return name;
    }

    // Getter and Setter for author
    public String getAuthor() {
        return author.get();
    }

    public void setAuthor(String author) {
        this.author.set(author);
    }

    public StringProperty authorProperty() {
        return author;
    }

    // Getter and Setter for price
    public String getPrice() {
        return price.get();
    }

    public void setPrice(String price) {
        this.price.set(price);
    }

    public StringProperty priceProperty() {
        return price;
    }

    // Getter and Setter for publishDate
    public String getPublishDate() {
        return publishDate.get();
    }

    public void setPublishDate(String publishDate) {
        this.publishDate.set(publishDate);
    }

    public StringProperty publishDateProperty() {
        return publishDate;
    }
}
