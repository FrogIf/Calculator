package frog.calculator.operater.oprimpl.dimpl.opr.round;

import frog.calculator.express.IExpression;
import frog.calculator.express.result.AResultExpression;
import frog.calculator.express.round.RoundOpenExpression;
import frog.calculator.operater.IOperator;

public class RoundOpenOperator implements IOperator{

    @Override
    public AResultExpression operate(IExpression expression) {
        RoundOpenExpression roundOpenExpression = (RoundOpenExpression) expression;
        if(roundOpenExpression.getContent() == null){
            throw new IllegalArgumentException("invalid expression.");
        }
        return roundOpenExpression.getContent().interpret();
    }

}
