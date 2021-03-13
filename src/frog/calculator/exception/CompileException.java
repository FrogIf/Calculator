package frog.calculator.exception;

/**
 * 表达式构建异常
 */
public class CompileException extends CalculatorExpression {

    private static final long serialVersionUID = 1L;

    public CompileException(String msg){
        super(msg);
    }

}
