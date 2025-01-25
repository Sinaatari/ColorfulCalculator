import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ColorfulCalculator {

    public static void main(String[] args) {
        // Create the frame for the calculator
        JFrame frame = new JFrame("Colorful Calculator");
        frame.setSize(400, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        // Create the text field to display calculations
        JTextField textField = new JTextField();
        textField.setFont(new Font("Arial", Font.PLAIN, 24));
        textField.setBackground(Color.CYAN);
        textField.setForeground(Color.BLACK);
        frame.add(textField, BorderLayout.NORTH);

        // Create the panel for buttons
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(4, 4, 10, 10));
        frame.add(panel, BorderLayout.CENTER);

        // Button color settings
        Color buttonColor = new Color(255, 165, 0); // Orange color for buttons

        // Create buttons for numbers and operations
        String[] buttons = {
            "7", "8", "9", "/",
            "4", "5", "6", "*",
            "1", "2", "3", "-",
            "0", ".", "=", "+"
        };

        // Add buttons to the panel
        for (String text : buttons) {
            JButton button = new JButton(text);
            button.setFont(new Font("Arial", Font.PLAIN, 24));
            button.setBackground(buttonColor);
            panel.add(button);

            // Add action listener to handle button clicks
            button.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    String command = e.getActionCommand();
                    if (command.equals("=")) {
                        try {
                            textField.setText(eval(textField.getText()));
                        } catch (Exception ex) {
                            textField.setText("Error");
                        }
                    } else if (command.equals("C")) {
                        textField.setText("");
                    } else {
                        textField.setText(textField.getText() + command);
                    }
                }
            });
        }

        // Make the frame visible
        frame.setVisible(true);
    }

    // Simple method to evaluate the mathematical expression
    public static String eval(String expression) {
        try {
            double result = new Object() {
                int pos = -1, c;

                void nextChar() {
                    c = (++pos < expression.length()) ? expression.charAt(pos) : -1;
                }

                boolean eat(int charToEat) {
                    while (c == ' ') nextChar();
                    if (c == charToEat) {
                        nextChar();
                        return true;
                    }
                    return false;
                }

                double parse() {
                    nextChar();
                    double x = parseExpression();
                    if (pos < expression.length()) throw new RuntimeException("Unexpected: " + (char) c);
                    return x;
                }

                double parseExpression() {
                    double x = parseTerm();
                    for (;;) {
                        if (eat('+')) x += parseTerm();
                        else if (eat('-')) x -= parseTerm();
                        else return x;
                    }
                }

                double parseTerm() {
                    double x = parseFactor();
                    for (;;) {
                        if (eat('*')) x *= parseFactor();
                        else if (eat('/')) x /= parseFactor();
                        else return x;
                    }
                }

                double parseFactor() {
                    if (eat('+')) return parseFactor();
                    if (eat('-')) return -parseFactor();

                    double x;
                    int startPos = this.pos;
                    if (eat('(')) {
                        x = parseExpression();
                        eat(')');
                    } else if ((c >= '0' && c <= '9') || c == '.') {
                        while ((c >= '0' && c <= '9') || c == '.') nextChar();
                        x = Double.parseDouble(expression.substring(startPos, this.pos));
                    } else {
                        throw new RuntimeException("Unexpected: " + (char) c);
                    }

                    return x;
                }
            }.parse();
            return String.valueOf(result);
        } catch (Exception e) {
            return "Error";
        }
    }
}
