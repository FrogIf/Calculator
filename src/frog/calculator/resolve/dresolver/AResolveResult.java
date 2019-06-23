package frog.calculator.resolve.dresolver;

import frog.calculator.express.IExpression;
import frog.calculator.resolve.IResolveResult;

public abstract class AResolveResult implements IResolveResult {

    abstract void setEndIndex(int index);

    abstract void setExpression(IExpression expression);

    abstract void setSymbol(String symbol);

}
