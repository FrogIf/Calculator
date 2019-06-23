package frog.calculator.operate.doubleopr.discriminator;

import frog.calculator.operate.IOperator;
import frog.calculator.operate.doubleopr.ADiscriminator;
import frog.calculator.operate.doubleopr.oprs.mid.AddDoubleOperator;
import frog.calculator.operate.doubleopr.oprs.mid.DivDoubleOperator;
import frog.calculator.operate.doubleopr.oprs.mid.MultDoubleOperator;
import frog.calculator.operate.doubleopr.oprs.mid.SubDoubleOperator;

public class MidOperatorDiscriminator extends ADiscriminator {

    @Override
    protected IOperator retrieve(String symbol) {
        IOperator operator = null;
        switch (symbol){
            case "+":
                operator = AddDoubleOperator.getInstance();
                break;
            case "-":
                operator = SubDoubleOperator.getInstance();
                break;
            case "*":
                operator = MultDoubleOperator.getInstance();
                break;
            case "/":
                operator = DivDoubleOperator.getInstance();
                break;
        }
        return operator;
    }
}
