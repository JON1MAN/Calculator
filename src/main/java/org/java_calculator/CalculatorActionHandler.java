package org.java_calculator;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class CalculatorActionHandler {

    private CalculatorState state = CalculatorState.INPUT_FIRST;
    private int firstValue = 0;
    private int secondValue = 0;
    private int lastSecondValue = 0;
    private String action = "";
    private String lastAction = "";
    private final JTextField textField;

    public CalculatorActionHandler(JTextField textField) {
        this.textField = textField;
    }

    public void handleNumber(int digit, ActionEvent event) {
        System.out.println("[ActionListener] Button " + event.getActionCommand());
        if (state == CalculatorState.AFTER_EQUALS) {
            textField.setText("");
            state = CalculatorState.INPUT_FIRST;
            action = "";
            lastAction = "";
        }

        textField.setText(textField.getText() + digit);

        if (state == CalculatorState.INPUT_OPERATOR) {
            textField.setText(String.valueOf(digit));
            state = CalculatorState.INPUT_SECOND;
        }
    }

    public void handleOperator(String operator, ActionEvent event) {
        System.out.println("[ActionListener] Button " + event.getActionCommand());
        switch (state) {
            case INPUT_FIRST, AFTER_EQUALS -> {
                try {
                    firstValue = Integer.parseInt(textField.getText());
                } catch (NumberFormatException e) {
                    firstValue = 0;
                }
                action = operator;
                state = CalculatorState.INPUT_OPERATOR;
            }
            case INPUT_OPERATOR -> {
                action = operator;
            }
            case INPUT_SECOND -> {
                try {
                    secondValue = Integer.parseInt(textField.getText());
                } catch (NumberFormatException e) {
                    secondValue = 0;
                }
                firstValue = calculate(firstValue, secondValue, action);
                handleDivideByZero(firstValue);
                action = operator;
                state = CalculatorState.INPUT_OPERATOR;
            }
        }
    }

    public void handleEquals() {
        System.out.println("[ActionListener] Button =");
        switch (state) {
            case INPUT_SECOND -> {
                try {
                    secondValue = Integer.parseInt(textField.getText());
                    lastSecondValue = secondValue;
                    lastAction = action;
                } catch (NumberFormatException e) {
                    secondValue = 0;
                }
                int result = calculate(firstValue, secondValue, action);
                handleDivideByZero(result);
                firstValue = result;
                state = CalculatorState.AFTER_EQUALS;
            }
            case INPUT_OPERATOR -> {
                secondValue = firstValue;
                lastSecondValue = secondValue;
                lastAction = action;
                int result = calculate(firstValue, secondValue, action);
                handleDivideByZero(result);
                firstValue = result;
                state = CalculatorState.AFTER_EQUALS;
            }
            case AFTER_EQUALS -> {
                int result = calculate(firstValue, lastSecondValue, lastAction);
                handleDivideByZero(result);
                firstValue = result;
            }
            case INPUT_FIRST -> {
                try {
                    firstValue = Integer.parseInt(textField.getText());
                } catch (NumberFormatException ignored) {
                }
                textField.setText(String.valueOf(firstValue));
                state = CalculatorState.AFTER_EQUALS;
            }
        }
    }

    public void handleClear() {
        System.out.println("[ActionListener] Button C");
        textField.setText("");
        firstValue = 0;
        secondValue = 0;
        lastSecondValue = 0;
        action = "";
        lastAction = "";
        state = CalculatorState.INPUT_FIRST;
    }

    public int showErrorText() {
        String errorText = "ERROR: Division by zero";
        this.textField.setForeground(Color.RED);
        this.textField.setText(errorText);
        return 0;
    }

    public int calculate(int firstValue, int secondValue, String action) {
        return switch (action) {
            case "+" -> firstValue + secondValue;
            case "-" -> firstValue - secondValue;
            case "/" -> (secondValue != 0) ? firstValue / secondValue : showErrorText();
            case "*" -> firstValue * secondValue;
            default -> firstValue;
        };
    }

    public void handleDivideByZero(int value) {
        if (value == 0 && action.equals("/")) {
            showErrorText();
        } else {
            textField.setText(String.valueOf(value));
        }
    }
}
