package org.java_calculator;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CalculatorUI extends JFrame implements ActionListener {

    private JTextField textField;
    private JButton[] numberButtons;
    private JButton[] functionButtons;

    private static final int CALCULATOR_WIDTH = 300;
    private static final int CALCULATOR_HEIGHT = 200;
    private static final int FONT_SIZE = 15;

    private static final String FONT = "Arial";
    private static final int BUTTON_WIDTH = 20;
    private static final int BUTTON_HEIGHT = FONT_SIZE;
    private final CalculatorActionHandler calculatorActionHandler;

    public CalculatorUI() throws HeadlessException {
        super("My first Calculator - v.0.1");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(CALCULATOR_WIDTH, CALCULATOR_HEIGHT);
        setLocationRelativeTo(null);
        setLayout(null);
        setResizable(false);

        buildCalculator();

        calculatorActionHandler = new CalculatorActionHandler(textField);
    }

    private void buildCalculator() {
        textField = inputField();
        add(textField);
        add(addKeyboard());
    }

    private JTextField inputField() {
        JTextField inputField = new JTextField();

        inputField.setBounds(5, 5, CALCULATOR_WIDTH - 18, (int) (CALCULATOR_HEIGHT * 0.12));
        inputField.setFont(new Font(FONT, Font.BOLD, FONT_SIZE));
        inputField.setHorizontalAlignment(JTextField.RIGHT);
        inputField.setEditable(false);

        return inputField;
    }

    private JPanel addKeyboard() {
        this.numberButtons = new JButton[10];
        this.functionButtons = new JButton[]{
                new JButton("+"),
                new JButton("-"),
                new JButton("*"),
                new JButton("/"),
                new JButton("C"),
                new JButton("=")
        };

        for (JButton functionButton : functionButtons) {
            functionButton.addActionListener(this);
            functionButton.setFont(new Font(FONT, Font.PLAIN, FONT_SIZE));
            functionButton.setPreferredSize(new Dimension(BUTTON_WIDTH, BUTTON_HEIGHT));
            functionButton.setFocusable(false);
        }

        for (int i = 0; i < numberButtons.length; i++) {
            numberButtons[i] = new JButton(String.valueOf(i));
            numberButtons[i].addActionListener(this);
            numberButtons[i].setFont(new Font(FONT, Font.PLAIN, FONT_SIZE));
            numberButtons[i].setPreferredSize(new Dimension(BUTTON_WIDTH, BUTTON_HEIGHT));
            numberButtons[i].setFocusable(false);
        }

        JPanel panel = new JPanel();
        panel.setBounds(5, 30, CALCULATOR_WIDTH - 20, CALCULATOR_HEIGHT - 60);
        panel.setLayout(new GridLayout(4, 4, 3, 3));

        panel.add(numberButtons[1]);
        panel.add(numberButtons[2]);
        panel.add(numberButtons[3]);
        panel.add(functionButtons[0]);

        panel.add(numberButtons[4]);
        panel.add(numberButtons[5]);
        panel.add(numberButtons[6]);
        panel.add(functionButtons[1]);

        panel.add(numberButtons[7]);
        panel.add(numberButtons[8]);
        panel.add(numberButtons[9]);
        panel.add(functionButtons[2]);

        panel.add(numberButtons[0]);
        panel.add(functionButtons[5]);
        panel.add(functionButtons[4]);
        panel.add(functionButtons[3]);

        return panel;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        this.textField.setForeground(Color.BLACK);
        var source = e.getSource();

        for (int i = 0; i < 10; i++) {
            if (source == numberButtons[i]) {
                calculatorActionHandler.handleNumber(i, e);
                return;
            }
        }

        if (source == functionButtons[0]) calculatorActionHandler.handleOperator("+", e);
        else if (source == functionButtons[1]) calculatorActionHandler.handleOperator("-", e);
        else if (source == functionButtons[2]) calculatorActionHandler.handleOperator("*", e);
        else if (source == functionButtons[3]) calculatorActionHandler.handleOperator("/", e);
        else if (source == functionButtons[4]) calculatorActionHandler.handleClear();
        else if (source == functionButtons[5]) calculatorActionHandler.handleEquals();
    }
}
