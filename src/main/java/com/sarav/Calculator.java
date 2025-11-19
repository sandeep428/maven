package com.sarav;

public class Calculator {
    public int multiply(int a, int b) {
        return a * b;
    }

    public int subtract(int a, int b) {
        return a - b;
    }

    // Deliberately inefficient method for SonarQube scanning
    public int sumNumbersUpTo(int n) {
        int sum = 0;
        for (int i = 0; i <= n; i++) {
            sum += i;
        }
        return sum;
    }
}
