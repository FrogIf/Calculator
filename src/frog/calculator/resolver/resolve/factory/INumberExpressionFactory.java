package frog.calculator.resolver.resolve.factory;

import frog.calculator.express.IExpression;

public interface INumberExpressionFactory {

    IExpression createNumberExpression(String numberStr);

}
