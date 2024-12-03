package com.example.demo;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;

public class DatabaseHelper {

    private static final String URL = "jdbc:mysql://localhost:4306/book";  // Cập nhật URL theo cơ sở dữ liệu
    private static final String USER = "pma";  // Tên đăng nhập của bạn
    private static final String PASSWORD = "";  // Mật khẩu của bạn

    public static Connection connect() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    public static ObservableList<Book> getAllBooks() {
        ObservableList<Book> bookList = FXCollections.observableArrayList();
        String query = "SELECT name, author, price, date FROM books";

        try (Connection conn = connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                String name = rs.getString("name");
                String author = rs.getString("author");
                String price = rs.getString("price");
                String publishDate = rs.getString("date");

                bookList.add(new Book(name, author, price, publishDate));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return bookList;
    }

    // Thêm sách mới vào cơ sở dữ liệu
    public static void addBook(Book book) {
        String query = "INSERT INTO books (name, author, price, date) VALUES (?, ?, ?, ?)";

        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, book.getName());
            pstmt.setString(2, book.getAuthor());
            pstmt.setString(3, book.getPrice());
            pstmt.setString(4, book.getPublishDate());

            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public static void updateBook(Book book) {
        String query = "UPDATE books SET name = ?, author = ?, price = ?, date = ? WHERE name = ?";

        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, book.getName());
            pstmt.setString(2, book.getAuthor());
            pstmt.setString(3, book.getPrice());
            pstmt.setString(4, book.getPublishDate());
            pstmt.setString(5, book.getName()); // assuming we use the name as a unique identifier

            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public static void deleteBook(Book book) {
        String query = "DELETE FROM books WHERE name = ?";  // Thay thế tên bằng khóa chính (id) nếu cần

        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, book.getName());  // Giả sử 'name' là khóa duy nhất

            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
