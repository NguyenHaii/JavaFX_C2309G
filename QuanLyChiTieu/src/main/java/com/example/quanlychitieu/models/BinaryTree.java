package com.example.quanlychitieu.models;

import java.util.ArrayList;
import java.util.List;

public class BinaryTree<T extends Comparable<T>> {
    private Node<T> root;

    public static class Node<T> {
        T data;
        Node<T> left;
        Node<T> right;

        public Node(T data) {
            this.data = data;
            this.left = null;
            this.right = null;
        }
    }

    // Add method to insert a new node
    public void add(T data) {
        root = addRecursive(root, data);
    }

    private Node<T> addRecursive(Node<T> current, T data) {
        if (current == null) {
            return new Node<>(data);
        }

        int comparison = data.compareTo(current.data);
        if (comparison < 0) {
            current.left = addRecursive(current.left, data);
        } else if (comparison > 0) {
            current.right = addRecursive(current.right, data);
        }

        return current;
    }

    public void insert(T data) {
        root = insertRec(root, data);
    }

    private Node<T> insertRec(Node<T> root, T data) {
        if (root == null) {
            root = new Node<>(data);  // Create a new node if the root is null
            return root;
        }

        // Compare the data with the root and insert accordingly
        if (data.compareTo(root.data) < 0) {
            root.left = insertRec(root.left, data);  // Insert in the left subtree
        } else if (data.compareTo(root.data) > 0) {
            root.right = insertRec(root.right, data);  // Insert in the right subtree
        }

        // Return the (unchanged) node pointer
        return root;
    }

    // In-order traversal
    public List<T> inOrderTraversal() {
        List<T> result = new ArrayList<>();
        inOrderTraversalRecursive(root, result);
        return result;
    }

    private void inOrderTraversalRecursive(Node<T> node, List<T> result) {
        if (node != null) {
            inOrderTraversalRecursive(node.left, result);
            result.add(node.data);
            inOrderTraversalRecursive(node.right, result);
        }
    }

    // Check if the tree contains a specific element
    public boolean contains(T data) {
        return containsRecursive(root, data);
    }

    private boolean containsRecursive(Node<T> current, T data) {
        if (current == null) {
            return false;
        }

        int comparison = data.compareTo(current.data);
        if (comparison < 0) {
            return containsRecursive(current.left, data);
        } else if (comparison > 0) {
            return containsRecursive(current.right, data);
        }

        return true; // Found
    }

    public List<T> filterByCategory(Expense.Type type, String category) {
        List<T> result = new ArrayList<>();
        filterByCategoryRecursive(root, result, type, category);
        return result;
    }

    private void filterByCategoryRecursive(Node<T> node, List<T> result, Expense.Type type, String category) {
        if (node != null) {
            // Đệ quy vào cây trái
            filterByCategoryRecursive(node.left, result, type, category);

            // Kiểm tra đối tượng Expense
            if (node.data instanceof Expense) {
                Expense expense = (Expense) node.data;
                if (expense.getType() == type && expense.getCategory().equals(category)) {
                    result.add(node.data); // Thêm vào kết quả lọc
                }
            }

            // Đệ quy vào cây phải
            filterByCategoryRecursive(node.right, result, type, category);
        }
    }


}
