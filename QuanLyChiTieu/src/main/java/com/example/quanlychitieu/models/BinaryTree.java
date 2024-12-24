package com.example.quanlychitieu.models;

import java.util.ArrayList;
import java.util.List;

public class BinaryTree<T extends Comparable<T>> {
    private Node<T> root;

    private static class Node<T> {
        T data;
        Node<T> left;
        Node<T> right;

        Node(T data) {
            this.data = data;
        }
    }

    // Thêm phần tử vào cây
    public void add(T data) {
        root = addRecursive(root, data);
    }

    private Node<T> addRecursive(Node<T> current, T data) {
        if (current == null) {
            return new Node<>(data);
        }

        if (data.compareTo(current.data) < 0) {
            current.left = addRecursive(current.left, data);
        } else if (data.compareTo(current.data) > 0) {
            current.right = addRecursive(current.right, data);
        }

        return current;
    }

    // Duyệt cây theo thứ tự tăng dần (In-order Traversal)
    public List<T> inOrderTraversal() {
        List<T> elements = new ArrayList<>();
        inOrderTraversalRecursive(root, elements);
        return elements;
    }

    private void inOrderTraversalRecursive(Node<T> node, List<T> elements) {
        if (node != null) {
            inOrderTraversalRecursive(node.left, elements);
            elements.add(node.data);
            inOrderTraversalRecursive(node.right, elements);
        }
    }
}