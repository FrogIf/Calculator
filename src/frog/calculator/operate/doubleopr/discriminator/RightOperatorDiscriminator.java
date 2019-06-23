package frog.calculator.operate.doubleopr.discriminator;

import frog.calculator.operate.IOperator;
import frog.calculator.operate.doubleopr.ADiscriminator;
import frog.calculator.operate.doubleopr.oprs.right.FactorialDoubleOperator;

public class RightOperatorDiscriminator extends ADiscriminator {

    @Override
    protected IOperator retrieve(String symbol) {
        IOperator operator = null;
        switch (symbol){
            case "!":
                operator = FactorialDoubleOperator.getInstance();
                break;
        }
        return operator;
    }

}
