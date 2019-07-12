package frog.calculator.register;

import frog.calculator.express.IExpression;
import frog.calculator.operater.IOperator;

public interface IRegister {

    void registe(String exp, IExpression expression, IOperator operator);

    void registe(String exp, IOperator operator);

    void registe(String exp, IExpression expression);

    IExpression getExpression();

    IOperator getOperator();

    IRegister retrieveRegistryInfo(char[] chars, int startIndex);
}
