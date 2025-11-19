package com.sarav;

import java.util.ArrayList;
import java.util.List;

public class App {
    public static void main(String[] args) {
        System.out.println("Hello from TomcatMavenApp!");
        
        App app = new App();
        System.out.println("Add 2 + 3 = " + app.add(2, 3));
        System.out.println("Divide 10 / 0 = " + app.safeDivide(10, 0));
        System.out.println("List created with " + app.createList(5).size() + " elements.");
    }
    
    // Simple addition
    public int add(int a, int b) {
        return a + b;
    }
    
    // Safe division: returns double, handles divide by zero
    public double safeDivide(double a, double b) {
        if (b == 0) {
            System.out.println("Cannot divide by zero!");
            return 0;
        }
        return a / b;
    }
    
    // Create a list of numbers
    public List<Integer> createList(int n) {
        List<Integer> list = new ArrayList<>();
        for (int i = 1; i <= n; i++) {
            list.add(i);
        }
        return list;
    }
