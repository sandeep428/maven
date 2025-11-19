package com.sarav;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class AppTest {

    @Test
    public void testAdd() {
        App app = new App();
        assertEquals(5, app.add(2, 3));
    }

    @Test
    public void testSafeDivide() {
        App app = new App();
        assertEquals(0, app.safeDivide(10, 0));
        assertEquals(2, app.safeDivide(4, 2));
    }

    @Test
    public void testCreateList() {
        App app = new App();
        assertEquals(5, app.createList(5).size());
    }

    @Test
    public void testCalculator() {
        Calculator calc = new Calculator();
        assertEquals(6, calc.multiply(2, 3));
        assertEquals(2, calc.subtract(5, 3));
        assertEquals(55, calc.sumNumbersUpTo(10));
    }
}
