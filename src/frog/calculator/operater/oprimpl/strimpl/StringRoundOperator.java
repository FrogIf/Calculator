package frog.calculator.operater.oprimpl.strimpl;

import frog.calculator.express.IExpression;
import frog.calculator.express.result.AResultExpression;
import frog.calculator.express.round.RoundOpenExpression;
import frog.calculator.operater.IOperator;

public class StringRoundOperator implements IOperator {

    @Override
    public AResultExpression operate(IExpression expression) {
        RoundOpenExpression roundOpenExpression = (RoundOpenExpression) expression;
        AResultExpression contentResult = roundOpenExpression.getContent().interpret();
        return new StringResultExpression(roundOpenExpression.symbol() + contentResult.resultValue() + roundOpenExpression.getCloseSymbol());
    }
}
