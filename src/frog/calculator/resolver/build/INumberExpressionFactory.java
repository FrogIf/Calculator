package frog.calculator.resolver.build;

import frog.calculator.express.IExpression;

public interface INumberExpressionFactory {

    IExpression createNumberExpression(String numberStr);

}
