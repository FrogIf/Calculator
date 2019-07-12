package frog.calculator.expressext.separator;

import frog.calculator.expressext.IExpression;

public interface IOperator {

    IExpression operate(SeparatorExpression expression);

}
