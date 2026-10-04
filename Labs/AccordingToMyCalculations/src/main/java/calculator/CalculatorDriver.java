package calculator;

public class CalculatorDriver {

    public static void main(String[] args) {
        Calculator calculator = new Calculator();

        String[] expressions = {
                "2+5",
                "3+6*5",
                "4*(2+3)",
                "(7+9)/8",
                "10-2-3",      // tests left-associativity
                "2+3*4-1",
                "((1+2)*(3+4))/7",
                "3.5+1.5*2"
        };

        for (String expression : expressions) {
            double result = calculator.evaluate(expression);
            System.out.println(expression + " = " + result);
        }
    }
}
