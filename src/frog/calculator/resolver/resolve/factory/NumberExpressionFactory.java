package frog.calculator.resolver.resolve.factory;

import frog.calculator.dimpl.opr.end.NumberDoubleOperator;
import frog.calculator.express.IExpression;
import frog.calculator.express.endpoint.EndPointExpression;
import frog.calculator.operator.NoRunOperator;

public class NumberExpressionFactory implements ISymbolExpressionFactory {

    private static final NumberDoubleOperator operator = new NumberDoubleOperator();

    @Override
    public IExpression createExpression(String symbol) {
        return new EndPointExpression(symbol, operator);
    }
}
