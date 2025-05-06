package org.java_calculator;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new CalculatorUI().setVisible(true);
        });
    }
}