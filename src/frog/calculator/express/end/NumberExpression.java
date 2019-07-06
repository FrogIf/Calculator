package frog.calculator.express.end;

import frog.calculator.express.context.IExpressContext;
import frog.calculator.operate.IOperator;

public class NumberExpression extends EndExpression {

    private final StringBuilder sb = new StringBuilder();

    public NumberExpression(IOperator operator, IExpressContext context) {
        super(operator, "", context);
    }

    public void assemble(char ch){
        sb.append(ch);
    }

    @Override
    public String symbol() {
        return sb.toString();
    }

    @Override
    public String toString(){
        return symbol();
    }

    public boolean isEmpty(){
        return sb.length() == 0;
    }

}
