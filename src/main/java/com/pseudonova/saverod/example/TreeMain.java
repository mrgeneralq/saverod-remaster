package com.pseudonova.saverod.example;

public class TreeMain {
    public static void main(String[] args) {
        Tree tree = new Tree(10);

        tree.registerFallListener(treE -> System.out.print("An apple fell from the tree!"));

        tree.dropApple();
    }
}
