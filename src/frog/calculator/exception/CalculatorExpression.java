package frog.calculator.exception;

public class CalculatorExpression extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public CalculatorExpression(){
        // do nothing
    }

    public CalculatorExpression(String msg){
        super(msg);
    }
    
}
