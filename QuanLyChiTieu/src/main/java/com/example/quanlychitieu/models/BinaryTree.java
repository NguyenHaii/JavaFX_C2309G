package com.example.quanlychitieu.models;

import java.util.ArrayList;
import java.util.List;

public class BinaryTree<T extends Comparable<T>> {  // Ensure T implements Comparable<T>
    private Node root;

    public static class Node {
        Expense expense;  // Ensure the right object type for Expense
        Node left;
        Node right;

        public Node(Expense expense) {
            this.expense = expense;
            this.left = null;
            this.right = null;
        }
    }

    // Add method to insert a new node
    public void add(T data) {
        root = addRecursive(root, data);
    }

    // Recursive method to add nodes
    private Node addRecursive(Node current, T data) {
        if (current == null) {
            return new Node((Expense) data);  // Cast data to Expense when adding
        }

        // Compare using the Comparable interface (compare by amount, category, etc.)
        int comparison = ((Expense) data).compareTo((Expense) current.expense);

        if (comparison < 0) {
            current.left = addRecursive(current.left, data);  // Go left if data is smaller
        } else if (comparison > 0) {
            current.right = addRecursive(current.right, data);  // Go right if data is larger
        }

        return current;
    }

    // Method to traverse the tree and return a list of expenses in order
    public List<Expense> inOrderTraversal() {
        List<Expense> expenses = new ArrayList<>();
        inOrderTraversalRecursive(root, expenses);
        return expenses;
    }

    // Recursive method for in-order traversal
    private void inOrderTraversalRecursive(Node node, List<Expense> expenses) {
        if (node != null) {
            inOrderTraversalRecursive(node.left, expenses);
            expenses.add(node.expense);  // Add the expense to the list
            inOrderTraversalRecursive(node.right, expenses);
        }
    }

    // Method to check if an expense already exists in the tree
    public boolean contains(Expense expense) {
        return containsRecursive(root, expense);
    }

    // Recursive check method
    private boolean containsRecursive(Node current, Expense expense) {
        if (current == null) {
            return false;  // Base case: if the node is null, the expense is not found
        }

        int comparison = expense.compareTo(current.expense);  // Compare expenses

        if (comparison < 0) {
            return containsRecursive(current.left, expense);  // Go left if expense is smaller
        } else if (comparison > 0) {
            return containsRecursive(current.right, expense);  // Go right if expense is larger
        }

        // If comparison == 0, the expense is already in the tree
        return true;
    }
}
