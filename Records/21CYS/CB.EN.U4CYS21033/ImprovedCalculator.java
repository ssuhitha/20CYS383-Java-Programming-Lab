import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Arrays;

public class ImprovedCalculator extends JFrame {

    private JTextField display;
    private String operator;
    private double operand1;
    private boolean isOperatorClicked;

    public ImprovedCalculator() {
        setTitle("Scientific Calculator");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Create the display field
        display = new JTextField(10);
        display.setEditable(false);

        // Create the number buttons
        JButton[] numberButtons = new JButton[10];
        for (int i = 0; i < 10; i++) {
            numberButtons[i] = new JButton(Integer.toString(i));
        }

        // Create the operator buttons
        JButton buttonPlus = new JButton("+");
        JButton buttonMinus = new JButton("-");
        JButton buttonMultiply = new JButton("*");
        JButton buttonDivide = new JButton("/");
        JButton buttonEquals = new JButton("=");

        // Create the clear button
        JButton buttonClear = new JButton("Clear");

        // Create the scientific function buttons
        JButton buttonSin = new JButton("sin");
        JButton buttonCos = new JButton("cos");
        JButton buttonTan = new JButton("tan");

        // Set layout
        setLayout(new BorderLayout());

        // Create panel for number buttons
        JPanel numberPanel = new JPanel();
        numberPanel.setLayout(new GridLayout(4, 3));
        Arrays.stream(numberButtons).forEach(numberPanel::add);
        numberPanel.add(buttonClear);

        // Create panel for operator buttons
        JPanel operatorPanel = new JPanel();
        operatorPanel.setLayout(new GridLayout(5, 1));
        operatorPanel.add(buttonPlus);
        operatorPanel.add(buttonMinus);
        operatorPanel.add(buttonMultiply);
        operatorPanel.add(buttonDivide);
        operatorPanel.add(buttonEquals);

        // Create panel for scientific function buttons
        JPanel scientificPanel = new JPanel();
        scientificPanel.setLayout(new GridLayout(3, 1));
        scientificPanel.add(buttonSin);
        scientificPanel.add(buttonCos);
        scientificPanel.add(buttonTan);

        // Create panel for display and buttons
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());
        mainPanel.add(display, BorderLayout.NORTH);
        mainPanel.add(numberPanel, BorderLayout.CENTER);
        mainPanel.add(operatorPanel, BorderLayout.EAST);
        mainPanel.add(scientificPanel, BorderLayout.WEST);

        // Add main panel to the content pane
        Container container = getContentPane();
        container.add(mainPanel);

        // Add action listeners to buttons
        ActionListener numberListener = new NumberButtonListener();
        Arrays.stream(numberButtons).forEach(button -> button.addActionListener(numberListener));

        ActionListener operatorListener = new OperatorButtonListener();
        buttonPlus.addActionListener(operatorListener);
        buttonMinus.addActionListener(operatorListener);
        buttonMultiply.addActionListener(operatorListener);
        buttonDivide.addActionListener(operatorListener);
        buttonEquals.addActionListener(operatorListener);

        ActionListener clearListener = new ClearButtonListener();
        buttonClear.addActionListener(clearListener);

        ActionListener scientificListener = new ScientificButtonListener();
        buttonSin.addActionListener(scientificListener);
        buttonCos.addActionListener(scientificListener);
        buttonTan.addActionListener(scientificListener);

        // Add key listener to the display field
        KeyListener keyListener = new DisplayKeyListener();
        display.addKeyListener(keyListener);

        pack();
        setLocationRelativeTo(null); // Center the frame on the screen
    }

    private class NumberButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            JButton button = (JButton) e.getSource();
            String buttonText = button.getText();
            display.setText(display.getText() + buttonText);
        }
    }

    private class OperatorButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            JButton button = (JButton) e.getSource();
            String currentText = display.getText();

            if (!isOperatorClicked) {
                operand1 = Double.parseDouble(currentText);
                operator = button.getText();
                display.setText("");
                isOperatorClicked = true;
            } else {
                double operand2 = Double.parseDouble(currentText);
                double result = performCalculation(operand1, operand2, operator);
                display.setText(String.valueOf(result));
                operand1 = result;
                operator = button.getText();
            }
        }

        private double performCalculation(double operand1, double operand2, String operator) {
            double result = 0.0;
            switch (operator) {
                case "+":
                    result = operand1 + operand2;
                    break;
                case "-":
                    result = operand1 - operand2;
                    break;
                case "*":
                    result = operand1 * operand2;
                    break;
                case "/":
                    result = operand1 / operand2;
                    break;
            }
            return result;
        }
    }

    private class ClearButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            display.setText("");
            operand1 = 0;
            operator = null;
            isOperatorClicked = false;
        }
    }

    private class ScientificButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            JButton button = (JButton) e.getSource();
            String buttonText = button.getText();
            String currentText = display.getText();
            double operand = Double.parseDouble(currentText);
            double result = 0.0;

            switch (buttonText) {
                case "sin":
                    result = Math.sin(operand);
                    break;
                case "cos":
                    result = Math.cos(operand);
                    break;
                case "tan":
                    result = Math.tan(operand);
                    break;
            }

            display.setText(String.valueOf(result));
        }
    }

    private class DisplayKeyListener implements KeyListener {
        @Override
        public void keyTyped(KeyEvent e) {
            // Not used
        }

        @Override
        public void keyPressed(KeyEvent e) {
            int keyCode = e.getKeyCode();

            // Handle numeric key inputs
            if (keyCode >= KeyEvent.VK_0 && keyCode <= KeyEvent.VK_9) {
                String digit = KeyEvent.getKeyText(keyCode);
                display.setText(display.getText() + digit);
            }

            // Handle operator key inputs
            if (keyCode == KeyEvent.VK_PLUS) {
                operatorButtonClicked("+");
            } else if (keyCode == KeyEvent.VK_MINUS) {
                operatorButtonClicked("-");
            } else if (keyCode == KeyEvent.VK_MULTIPLY) {
                operatorButtonClicked("*");
            } else if (keyCode == KeyEvent.VK_DIVIDE) {
                operatorButtonClicked("/");
            } else if (keyCode == KeyEvent.VK_EQUALS || keyCode == KeyEvent.VK_ENTER) {
                operatorButtonClicked("=");
            }
        }

        private void operatorButtonClicked(String op) {
            String currentText = display.getText();

            if (!isOperatorClicked) {
                operand1 = Double.parseDouble(currentText);
                operator = op;
                display.setText("");
                isOperatorClicked = true;
            } else {
                double operand2 = Double.parseDouble(currentText);
                double result = performCalculation(operand1, operand2, operator);
                display.setText(String.valueOf(result));
                operand1 = result;
                operator = op;
            }
        }

        private double performCalculation(double operand1, double operand2, String operator) {
            double result = 0.0;
            switch (operator) {
                case "+":
                    result = operand1 + operand2;
                    break;
                case "-":
                    result = operand1 - operand2;
                    break;
                case "*":
                    result = operand1 * operand2;
                    break;
                case "/":
                    result = operand1 / operand2;
                    break;
            }
            return result;
        }

        @Override
        public void keyReleased(KeyEvent e) {
            // Not used
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new ImprovedCalculator().setVisible(true);
            }
        });
    }
}
