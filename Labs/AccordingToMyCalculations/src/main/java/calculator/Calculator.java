package calculator;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;
import java.util.Map;

public class Calculator {

    private static final Map<Character, Integer> PRECEDENCE = Map.of(
            '+', 1,
            '-', 1,
            '*', 2,
            '/', 2
    );


    public double evaluate(String infixExpression) {
        List<String> tokens = tokenize(infixExpression);
        List<String> postfixTokens = infixToPostfix(tokens);
        return evaluatePostfix(postfixTokens);
    }

    private List<String> tokenize(String expression) {
        List<String> tokens = new ArrayList<>();
        int i = 0;

        while (i < expression.length()) {
            char c = expression.charAt(i);

            if (Character.isDigit(c) || c == '.') {
                StringBuilder number = new StringBuilder();
                while (i < expression.length()
                        && (Character.isDigit(expression.charAt(i)) || expression.charAt(i) == '.')) {
                    number.append(expression.charAt(i));
                    i++;
                }
                tokens.add(number.toString());
            } else if (c == '+' || c == '-' || c == '*' || c == '/' || c == '(' || c == ')') {
                tokens.add(String.valueOf(c));
                i++;
            } else {
                i++;
            }
        }

        return tokens;
    }
    private List<String> infixToPostfix(List<String> tokens) {
        List<String> output = new ArrayList<>();
        Deque<Character> operatorStack = new ArrayDeque<>();

        for (String token : tokens) {
            char c = token.charAt(0);

            if (isNumber(token)) {
                output.add(token);
            } else if (c == '(') {
                operatorStack.push(c);
            } else if (c == ')') {
                while (!operatorStack.isEmpty() && operatorStack.peek() != '(') {
                    output.add(String.valueOf(operatorStack.pop()));
                }
                operatorStack.pop(); // discard the matching '('
            } else {
                // c is an operator: +, -, *, /
                while (!operatorStack.isEmpty()
                        && operatorStack.peek() != '('
                        && PRECEDENCE.get(operatorStack.peek()) >= PRECEDENCE.get(c)) {
                    output.add(String.valueOf(operatorStack.pop()));
                }
                operatorStack.push(c);
            }
        }

        // Pop any remaining operators onto the output.
        while (!operatorStack.isEmpty()) {
            output.add(String.valueOf(operatorStack.pop()));
        }

        return output;
    }

    private double evaluatePostfix(List<String> postfixTokens) {
        Deque<Double> stack = new ArrayDeque<>();

        for (String token : postfixTokens) {
            if (isNumber(token)) {
                stack.push(Double.parseDouble(token));
            } else {
                double b = stack.pop(); // second operand (popped first)
                double a = stack.pop(); // first operand
                double result = applyOperator(a, b, token.charAt(0));
                stack.push(result);
            }
        }

        return stack.pop();
    }

    private double applyOperator(double a, double b, char operator) {
        switch (operator) {
            case '+':
                return a + b;
            case '-':
                return a - b;
            case '*':
                return a * b;
            case '/':
                return a / b;
            default:
                throw new IllegalArgumentException("Unsupported operator: " + operator);
        }
    }

    private boolean isNumber(String token) {
        char first = token.charAt(0);
        return Character.isDigit(first) || first == '.';
    }
}
