package frog.calculator.resolver;

import frog.calculator.expression.IExpression;

public abstract class AResolveResult implements IResolveResult{

    abstract void setEndIndex(int index);

    abstract void setExpression(IExpression expression);

}
